package com.ac.reserve.web.scheduled;

import com.ac.reserve.web.api.po.Bill;
import com.ac.reserve.web.api.service.BillService;
import com.ac.reserve.web.examineapi.service.ExamineApiService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

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


    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void checkExamineResult() {
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("state", 0);
        List<Bill> list = billService.list(billQueryWrapper);
        if (list != null && list.size() > 0) {
            for (Bill bill : list) {
                String examineId = bill.getExamineId();
                JSONObject jsonObject = examineApiService.checkExamineResult(examineId);
                // 审核失败
                if (jsonObject == null || CHECK_SUCCESS.equals(jsonObject.getString(CHECK_FIELD))) {
                    bill.setState(2);
                }
                // 审核成功
                else if (CHECK_FAIL.equals(jsonObject.getString(CHECK_FIELD))) {
                    bill.setState(1);
                }
            }
            billService.updateBatchById(list);
        }
    }
}