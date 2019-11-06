package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.common.constant.DataSourceConstant;
import com.ac.reserve.common.dto.wechat.CredentialResponseDTO;
import com.ac.reserve.common.dto.wechat.LoginResponseDTO;
import com.ac.reserve.common.exception.ServiceException;
import com.ac.reserve.web.api.po.User;
import com.ac.reserve.web.api.service.UserService;
import com.ac.reserve.web.api.service.WeChatService;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.ac.reserve.common.constant.WeChatUrl.GET_ACCESS_TOKEN;
import static com.ac.reserve.common.constant.WeChatUrl.JS_CODE_2_SESSION;


@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {
    // redis登录保存token前缀
    private static final String REDIS_LOGIN_TOKEN_KEY = "BEARER_TOKEN_";
    // token 保存时间 2小时
    private static final Long REDIS_LOGIN_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 2L;

    @Value("${weChat.appid}")
    private String appid;

    @Value("${weChat.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("weChatRestTemplate")
    private RestTemplate weChatRestTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getSessionInfo(String code) {
        LoginResponseDTO loginInfo = getLoginResponseDTO(code);
        // 唯一标识 id
        String openid = loginInfo.getOpenid();
        // 随机生成 UUID token
        String accessToken = REDIS_LOGIN_TOKEN_KEY + UUID.randomUUID().toString();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        wrapper.eq("valid", DataSourceConstant.DATA_SOURCE_VALID);
        User user = userService.getOne(wrapper);
        Date now = new Date();
        // 用户已存在
        if (user != null && StringUtils.isNotBlank(user.getOpenid())) {
            user.setAccesstoken(accessToken);
            user.setUpdated(now);
            user.setSessionKey(loginInfo.getSession_key());
        } else {
            user = User.builder()
                    .openid(openid)
                    .sessionKey(loginInfo.getSession_key())
                    .accesstoken(accessToken)
                    .created(now)
                    .updated(now)
                    .build();
        }
        userService.saveOrUpdate(user);
        redisTemplate.opsForValue().set(accessToken, openid, REDIS_LOGIN_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
        return user.getOpenid();
    }

    /**
     * 根据code获取登录参数
     *
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

        JSONObject response = weChatRestTemplate.getForObject(url, JSONObject.class, params);
        if (response == null) {
            throw new ServiceException("微信登录失败.");
        }

        if (response.get("errcode") != null) {
            if ("40029".equals(response.getString("errcode"))) {
                throw new ServiceException("code 失效.");
            } else if (!"0".equals(response.getString("errcode"))) {
                throw new ServiceException("非法 code," + response.getString("errmsg"));
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

        CredentialResponseDTO response = weChatRestTemplate.getForObject(url, CredentialResponseDTO.class, params);
        return response;

    }

    /**
     * get url 占位符
     *
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
