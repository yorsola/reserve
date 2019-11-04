package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.web.api.entity.User;
import com.ac.reserve.web.api.mapper.UserMapper;
import com.ac.reserve.web.api.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-11-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
}
