package com.ac.reserve.common.interceptor;

import com.ac.reserve.common.utils.JsonUtil;
import com.ac.reserve.common.utils.RedisUtil;
import com.ac.reserve.common.utils.ResponseBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    // redis登录保存token前缀
    private static final String REDIS_LOGIN_TOKEN_KEY = "login_";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("accessToken");
        if (StringUtils.isNotBlank(accessToken) && redisUtil.hasKey(REDIS_LOGIN_TOKEN_KEY + accessToken)) {
            return true;
        }
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JsonUtil.toJSON(ResponseBuilder.buildError("Invalid accessToken")));
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
