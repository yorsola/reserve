package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.ac.reserve.web.api.dto.CampaignDTO;
import com.ac.reserve.web.api.service.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign")
@CrossOrigin("*")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @ApiOperation(value = "获取活动相关信息")
    @ApiResponses({
            @ApiResponse(code = 0000, message = "请求成功"),
            @ApiResponse(code = 1000, message = "请求失败")})
    @GetMapping("/info")
    public BaseResponse getCampaignInfo() {
        CampaignDTO campaignDTO = campaignService.getEffectiveCampaign();
        return ResponseBuilder.buildSuccess(campaignDTO);
    }
}
