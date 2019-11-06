package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.common.dto.wechat.CredentialResponseDTO;
import com.ac.reserve.common.dto.wechat.LoginResponseDTO;
import com.ac.reserve.common.exception.ServiceException;
import com.ac.reserve.common.utils.RedisUtil;
import com.ac.reserve.common.utils.RestUtil;
import com.ac.reserve.web.api.po.User;
import com.ac.reserve.web.api.service.UserService;
import com.ac.reserve.web.api.service.WeChatService;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.ac.reserve.common.constant.WeChatUrl.GET_ACCESS_TOKEN;
import static com.ac.reserve.common.constant.WeChatUrl.JS_CODE_2_SESSION;


@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {
    // redis登录保存token前缀
    private static final String REDIS_LOGIN_TOKEN_KEY = "login_";
    // token 保存时间 6小时
    private static final Long REDIS_LOGIN_TOKEN_TIME = 1000 * 60 * 60 * 6L;

    @Value("${weChat.appid}")
    private String appid;

    @Value("${weChat.secret}")
    private String secret;

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RestUtil restUtil;

    @Override
    public String login(String code){
        LoginResponseDTO loginInfo = getLoginResponseDTO(code);
        // 唯一标识 id
        String openid = loginInfo.getOpenid();
        // 随机生成 UUID token
        String accessToken = REDIS_LOGIN_TOKEN_KEY + UUID.randomUUID().toString();
        redisUtil.set(accessToken, openid, REDIS_LOGIN_TOKEN_TIME);
        User user = User.builder()
                .openid(openid)
                .sessionKey(loginInfo.getSession_key())
                .build();
        userService.save(user);
       return accessToken;
    }

    /**
     * 根据code获取登录参数
     * @param code
     * @return
     */
    private LoginResponseDTO getLoginResponseDTO(String code) {
        String url = JS_CODE_2_SESSION.getUrl();
        Map<String, String> params = new HashMap();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");

        url = expandURL(url, params.keySet());

        JSONObject response = restTemplate.getForObject(url, JSONObject.class, params);
        if (response == null) {
            throw new ServiceException("微信登录失败");
        }
        if (response.get("errcode") != null) {
            if ("40029".equals(response.getString("errcode"))) {
                throw new ServiceException("code 失效");
            } else if (!"0".equals(response.getString("errcode"))) {
                throw new ServiceException(response.getString("errmsg"));
            }
        }
        LoginResponseDTO loginResponseDTO = JSONObject.toJavaObject(response, LoginResponseDTO.class);
        return loginResponseDTO;

    }

    private CredentialResponseDTO getCredentialResponseDTO() {
        String url = GET_ACCESS_TOKEN.getUrl();
        Map<String, String> params = new HashMap();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("grant_type", "client_credential");
        url = expandURL(url, params.keySet());

        CredentialResponseDTO response = restTemplate.getForObject(url, CredentialResponseDTO.class, params);
        return response;

    }

    /**
     * get url 占位符
     * @param url
     * @param keys
     * @return
     */
    private static String expandURL(String url, Set<?> keys) {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        for (Object key : keys) {
            sb.append(key).append("=").append("{").append(key).append("}").append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
