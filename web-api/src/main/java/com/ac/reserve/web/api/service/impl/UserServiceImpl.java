package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.web.api.po.User;
import com.ac.reserve.web.api.mapper.UserMapper;
import com.ac.reserve.web.api.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

}
