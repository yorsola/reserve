package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.common.utils.JsonUtil;
import com.ac.reserve.common.utils.RedisUtil;
import com.ac.reserve.common.utils.RestUtil;
import com.ac.reserve.web.api.domain.User;
import com.ac.reserve.web.api.service.WeChatService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

import static com.ac.reserve.common.constant.WeChatUrl.JS_CODE_2_SESSION;


@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    @Value("${weChat.appid}")
    private String appid;

    @Value("${weChat.secret}")
    private String secret;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestUtil restUtil;

    @Override
    @Cacheable(value = "UserInfo",key ="#code",keyGenerator = "")
    public User login(String code){
        User user = getUser(code);
        user.setId(1L);
        String accessToken = UUID.randomUUID().toString();
        user.setAccessToken(accessToken);
        return user;
    }

    private User getUser(String code){
        HashMap<String, String>  requestBody= new HashMap<>();
        requestBody.put("appid",appid);
        requestBody.put("secret",secret);
        requestBody.put("js_code",code);
        requestBody.put("grant_type","authorization_code");
        String result = restUtil.httpGet(JS_CODE_2_SESSION.getUrl(), requestBody);
        User user = null;
        if(StringUtils.isNotBlank(result)){
            user = JsonUtil.toBean(result,User.class);
        }
        return user;
    }
}
