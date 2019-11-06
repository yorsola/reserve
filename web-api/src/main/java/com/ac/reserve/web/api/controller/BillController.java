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
import java.util.Date;
import java.util.List;

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
    @GetMapping("/{openid}")
    public BaseResponse getBillInfo(@PathVariable(value = "openid",required = true)String openid) {
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("openid",openid);
        billQueryWrapper.eq("valid", DataSourceConstant.DATA_SOURCE_VALID);
        List<Bill> list = billService.list(billQueryWrapper);
        return ResponseBuilder.buildSuccess(list);
    }

    public static void main(String[] args) {
        List<BillRequestDTO> billRequestDTOS = new ArrayList<>();
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setDocumentType("111");
        billRequestDTO.setPossessorNumber("440801199411262471");
        billRequestDTO.setPossessorImg("http://localhost:8080/reserve/v1/api/bill/reserve");
        billRequestDTO.setPossessorName("李四");
        billRequestDTO.setPossessorPhone("128109012");
        billRequestDTO.setRoundId(1);
        billRequestDTO.setType(1);
        billRequestDTOS.add(billRequestDTO);

        String s = JSONUtil.toJSONString(billRequestDTOS);
        System.out.println(s);
    }

    @ApiOperation(value = "立即预约")
    @PostMapping("/reserve")
    public BaseResponse reserve(@RequestBody @Valid List<BillRequestDTO> billRequestDTOS){
        Bill bill = null;
        String bsId = null;
        List<Bill> list = new ArrayList<>();
        Date now = new Date();
        for (BillRequestDTO requestDTO : billRequestDTOS) {
            bill = new Bill();
            BeanUtils.copyProperties(requestDTO, bill);
            JSONObject jsonObject = examineApiService.applyExamine(requestDTO);
            if (jsonObject == null) {
                bill.setState(DataSourceConstant.APPROVAL_FAILED);
                continue;
            }

            // 申请成功
            if (APPLY_EXAMINE_SUCCESS.equals(jsonObject.getString("code"))) {
                bill.setState(DataSourceConstant.APPROVAL_IN_HAND);
                JSONObject data = jsonObject.getJSONObject("data");
                bsId = data.getString("bsId");
                bill.setExamineId(bsId);
                //todo 二维码
                // 发送提交预约短信
                SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_SUBMIT, SIGN_NAME, bill.getPossessorPhone(), null);
                // 生成二维码和条形码
                String codeValue = GetRandomString.getRandomString(8) + System.currentTimeMillis();
            }
            // 申请失败
            else {
                bill.setState(DataSourceConstant.APPROVAL_FAILED);
                // 发送预约失败短信
                SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_TD, SIGN_NAME, bill.getPossessorPhone(), null);
            }
            bill.setCreated(now);
            bill.setUpdated(now);
            list.add(bill);
        }
        billService.saveBatch(list);
        return ResponseBuilder.buildSuccess("预约成功.");
    }
}
