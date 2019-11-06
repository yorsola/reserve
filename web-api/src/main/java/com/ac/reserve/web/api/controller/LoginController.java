package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.ac.reserve.web.api.dto.UserDTO;
import com.ac.reserve.web.api.po.User;
import com.ac.reserve.web.api.service.UserService;
import com.ac.reserve.web.api.service.WeChatService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("")
    public BaseResponse login(@RequestParam(value = "code", required = true) String code) {

        String openid = weChatService.getSessionInfo(code);
        UserDTO userDTO = new UserDTO();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        User user = userService.getOne(wrapper);
        BeanUtils.copyProperties(user,userDTO);

        return ResponseBuilder.buildSuccess(userDTO);
    }

}
