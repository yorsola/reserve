package com.ac.reserve.web.api.controller;


import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.utils.ResponseBuilder;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdf")
@CrossOrigin("*")
public class DownloadController {


    @ApiOperation(value = "下载PDF")
    @GetMapping("/")
    public BaseResponse downloadPdf() {
        return ResponseBuilder.buildSuccess();
    }
}
