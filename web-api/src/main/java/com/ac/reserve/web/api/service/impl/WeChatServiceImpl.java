package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.common.dto.wechat.CredentialResponse;
import com.ac.reserve.common.dto.wechat.LoginResponse;
import com.ac.reserve.common.utils.RedisUtil;
import com.ac.reserve.common.utils.RestUtil;
import com.ac.reserve.web.api.domain.User;
import com.ac.reserve.web.api.service.WeChatService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.ac.reserve.common.constant.WeChatUrl.GET_ACCESS_TOKEN;
import static com.ac.reserve.common.constant.WeChatUrl.JS_CODE_2_SESSION;


@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    @Value("${weChat.appid}")
    private String appid;

    @Value("${weChat.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestUtil restUtil;

    @Override
    public User login(String code){
        LoginResponse loginInfo = getLoginInfo(code);
        CredentialResponse credential = getAccessToken();
//        String accessToken = UUID.randomUUID().toString();
        return null;
    }

    private LoginResponse getLoginInfo(String code) {
        String url = JS_CODE_2_SESSION.getUrl()+
                "?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}";
        Map<String, String> params = new HashMap();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");

        LoginResponse response = restTemplate.getForObject(url, LoginResponse.class, params);
        return response;

    }

    private CredentialResponse getAccessToken() {
        String url = GET_ACCESS_TOKEN.getUrl()+
                "?appid={appid}&secret={secret}&grant_type={grant_type}";
        Map<String, String> params = new HashMap();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("grant_type", "client_credential");

        CredentialResponse response = restTemplate.getForObject(url, CredentialResponse.class, params);
        return response;

    }
}
