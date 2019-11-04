package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.ac.reserve.web.api.dto.CampaignDTO;
import com.ac.reserve.web.api.po.Campaign;
import com.ac.reserve.web.api.service.CampaignService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @ApiOperation(value = "获取活动信息")
    @GetMapping("/info")
    public BaseResponse getCampaignInfo() {
        CampaignDTO campaignDTO = campaignService.getEffectiveCampaign();
        return ResponseBuilder.buildSuccess(campaignDTO);
    }
}
