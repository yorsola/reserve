package com.ac.reserve.common.config.Interceptor;

import com.ac.reserve.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor webLoginInterceptor;
    final String[] notPaths = {"/webjars/**", "/swagger-*", "/login/**"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ///往拦截器注册器中注册自己的拦截器，并添加拦截路径
        registry.addInterceptor(webLoginInterceptor)
                .addPathPatterns("/**").excludePathPatterns(notPaths);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}