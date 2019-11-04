package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.constant.DataSourceConstant;
import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.ac.reserve.web.api.po.Bill;
import com.ac.reserve.web.api.service.BillService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

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
}
