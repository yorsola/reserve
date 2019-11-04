package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.utils.ResponseBuilder;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    @ApiOperation(value = "获取活动信息")
    @GetMapping("/info")
    public BaseResponse getCampaignInfo() {
        return ResponseBuilder.buildSuccess();
    }
}
