package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.domain.BaseResponse;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.ac.reserve.web.api.domain.User;
import com.ac.reserve.web.api.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private WeChatService weChatService;

    @PostMapping("")
    public BaseResponse login(@RequestParam(value = "code", required = false) String code) {

        User user = weChatService.login(code);
        return ResponseBuilder.buildSuccess(user);
    }

}
