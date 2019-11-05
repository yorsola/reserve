package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.constant.DataSourceConstant;
import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.ac.reserve.web.api.dto.ReserveBillRequestDTO;
import com.ac.reserve.web.api.po.Bill;
import com.ac.reserve.web.api.service.BillService;
import com.ac.reserve.web.examineapi.dto.ApplyExaminePostRequestDTO;
import com.ac.reserve.web.examineapi.service.ExamineApiService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private ExamineApiService examineApiService;

    private static final String APPLY_EXAMINE_SUCCESS = "1";

    @ApiOperation(value = "获取票据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "用户id", required = true,  paramType = "path"),
    })
    @GetMapping("/{userid}")
    public BaseResponse getCampaignInfo(@PathVariable(value = "userid",required = true)Integer userId) {
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id",userId);
        billQueryWrapper.eq("valid", DataSourceConstant.DATA_SOURCE_VALID);
        List<Bill> list = billService.list(billQueryWrapper);
        return ResponseBuilder.buildSuccess(list);
    }

    @ApiOperation(value = "预约")
    @PostMapping("/")
    public BaseResponse fun(@RequestBody @Valid List<ReserveBillRequestDTO> billRequestDTOS){
        Bill bill;
        List<Bill> list = new ArrayList<>();
        for (ReserveBillRequestDTO requestDTO : billRequestDTOS) {
            JSONObject jsonObject = examineApiService.applyExamine(requestDTO);
            if (jsonObject == null) {
                continue;
            }
            bill = new Bill();
            BeanUtils.copyProperties(requestDTO, bill);
            // 申请成功
            if (APPLY_EXAMINE_SUCCESS.equals(jsonObject.getString("code"))) {
                bill.setState(0);
                JSONObject data = jsonObject.getJSONObject("data");
                String bsId = data.getString("bsId");
                bill.setExamineId(bsId);
            }
            // 申请失败
            else {
                bill.setState(2);
            }
            list.add(bill);
        }
        billService.saveBatch(list);
        return ResponseBuilder.buildSuccess("预约成功");
    }
}