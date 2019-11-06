package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.constant.CommonConstant;
import com.ac.reserve.common.constant.DataSourceConstant;
import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.util.SmsUtil;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.ac.reserve.web.api.dto.BillRequestDTO;
import com.ac.reserve.web.api.po.Bill;
import com.ac.reserve.web.api.service.BillService;
import com.ac.reserve.web.api.service.ExamineApiService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value="bill_controller",tags={"票据接口"})
@RestController
@RequestMapping("/bill")
@CrossOrigin("*")
public class BillController {

    // 申请成功code
    private static final String APPLY_EXAMINE_SUCCESS = "1";
    // 短信签名（【珠海航展】）
    private static final String SIGN_NAME = "【珠海航展】";

    @Autowired
    private BillService billService;
    @Autowired
    private ExamineApiService examineApiService;

    @Autowired
    private SmsUtil smsUtil;

    @ApiOperation(value = "获取票据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "用户id", required = true,  paramType = "path"),
    })
    @GetMapping("/{userid}")
    public BaseResponse getBillInfo(@PathVariable(value = "userid",required = true)Integer userId) {
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id",userId);
        billQueryWrapper.eq("valid", DataSourceConstant.DATA_SOURCE_VALID);
        List<Bill> list = billService.list(billQueryWrapper);
        return ResponseBuilder.buildSuccess(list);
    }

    @ApiOperation(value = "立即预约")
    @PostMapping("/reserve")
    public BaseResponse reserve(@RequestBody @Valid List<BillRequestDTO> billRequestDTOS){
        Bill bill = null;
        String bsId = null;
        List<Bill> list = new ArrayList<>();
        for (BillRequestDTO requestDTO : billRequestDTOS) {
            JSONObject jsonObject = examineApiService.applyExamine(requestDTO);
            if (jsonObject == null) {
                continue;
            }
            bill = new Bill();
            BeanUtils.copyProperties(requestDTO, bill);
            // 申请成功
            if (APPLY_EXAMINE_SUCCESS.equals(jsonObject.getString("code"))) {
                bill.setState(DataSourceConstant.APPROVAL_IN_HAND);
                JSONObject data = jsonObject.getJSONObject("data");
                bsId = data.getString("bsId");
                bill.setExamineId(bsId);
                // 发送 提交预约 短信
                SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_SUBMIT, SIGN_NAME, bill.getPossessorPhone(), null);
            }
            // 申请失败
            else {
                bill.setState(DataSourceConstant.APPROVAL_FAILED);
                // 发送预约失败短信
                SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_TD, SIGN_NAME, bill.getPossessorPhone(), null);
            }
            list.add(bill);
        }
        billService.saveBatch(list);
        return ResponseBuilder.buildSuccess("预约成功.");
    }
}
