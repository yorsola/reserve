package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.ac.reserve.web.api.po.User;
import com.ac.reserve.web.api.service.WeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Api(value="ogin_controller",tags={"登录接口"})
@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private WeChatService weChatService;
//    @ApiParam(name="id",value="用户id",required=true) Long id,@ApiParam(name="username",value="用户名"
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "授权码", required = true, dataType = "code", paramType = "query"),
    })
    @PostMapping("")
    public BaseResponse login(@RequestParam(value = "code", required = true) String code) {
        String accessToken = weChatService.login(code);
        HashMap<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        return ResponseBuilder.buildSuccess(map);
    }

}
