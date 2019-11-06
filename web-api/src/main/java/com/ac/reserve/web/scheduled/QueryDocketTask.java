package com.ac.reserve.web.scheduled;

import com.ac.reserve.common.constant.CommonConstant;
import com.ac.reserve.common.constant.DataSourceConstant;
import com.ac.reserve.common.util.SmsUtil;
import com.ac.reserve.web.api.po.Bill;
import com.ac.reserve.web.api.service.BillService;
import com.ac.reserve.web.api.service.ExamineApiService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configurable
public class QueryDocketTask {

    @Autowired
    private BillService billService;

    @Autowired
    private ExamineApiService examineApiService;

    @Value("${examine.account.user}")
    private String user;

    @Value("${examine.account.password}")
    private String password;

    private static final String CHECK_FIELD = "zzbs";
    private static final String CHECK_SUCCESS = "0";
    private static final String CHECK_FAIL = "1";
    // 短信签名（【珠海航展】）
    private static final String SIGN_NAME = "【珠海航展】";


    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void checkExamineResult() {
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("state", DataSourceConstant.APPROVAL_IN_HAND)
                .eq("valid", DataSourceConstant.DATA_SOURCE_VALID);
        List<Bill> list = billService.list(billQueryWrapper);
        if (list != null && list.size() != 0) {
            for (Bill bill : list) {
                String examineId = bill.getExamineId();
                JSONObject jsonObject = examineApiService.checkExamineResult(examineId);
                // 审核失败
                if (jsonObject == null || CHECK_SUCCESS.equals(jsonObject.getString(CHECK_FIELD))) {
                    bill.setState(DataSourceConstant.APPROVAL_FAILED);
                    SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_TD, SIGN_NAME, bill.getPossessorPhone(), null);
                }
                // 审核成功
                if (CHECK_FAIL.equals(jsonObject.getString(CHECK_FIELD))) {
                    bill.setState(DataSourceConstant.APPROVAL_SUCCESS);
                    //todo 二维码
                    SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_PS, SIGN_NAME, bill.getPossessorPhone(), null);

                }
            }
            billService.updateBatchById(list);
        }
    }
}
