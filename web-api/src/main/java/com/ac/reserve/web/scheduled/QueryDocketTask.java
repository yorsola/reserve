package com.ac.reserve.web.scheduled;

import com.ac.reserve.common.constant.CommonConstant;
import com.ac.reserve.common.constant.DataSourceConstant;
import com.ac.reserve.common.util.GetRandomString;
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

    // 备审状态：1:背审通过,0:背审不通过
    private static final String BS_CHECK_FIELD = "zzbs";
    // 备审通过编码
    private static final String BS_CHECK_SUCCESS = "1";
    // 备审不通过
    private static final String BS_CHECK_FAIL = "0";
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
                if (jsonObject == null || BS_CHECK_FAIL.equals(jsonObject.getString(BS_CHECK_FIELD))) {
                    bill.setState(DataSourceConstant.APPROVAL_FAILED);
                    SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_TD, SIGN_NAME, bill.getPossessorPhone(), null);
                }
                // 审核成功
                if (BS_CHECK_SUCCESS.equals(jsonObject.getString(BS_CHECK_FIELD))) {
                    bill.setState(DataSourceConstant.APPROVAL_SUCCESS);

                    JSONObject smsJson = new JSONObject();

                    smsJson.put("date", "2019.12.20 20:00");
                    smsJson.put("startTime", "16点");
                    smsJson.put("endTime", "20点");
                    smsJson.put("addressA", "昌盛路至粤海东路");
                    smsJson.put("addressB", "粤海东路至联安路");
                    smsJson.put("addressC", "联安路至水湾路");
                    smsJson.put("addressD", "水湾路至情侣南路");
                    smsJson.put("addressE", "情侣南路");
                    SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_PS, SIGN_NAME, bill.getPossessorPhone(), smsJson.toJSONString());
                    String codeValue = GetRandomString.getRandomString(8) + System.currentTimeMillis();
                    bill.setCode(codeValue);
                }
            }
            billService.updateBatchById(list);
        }
    }
}
