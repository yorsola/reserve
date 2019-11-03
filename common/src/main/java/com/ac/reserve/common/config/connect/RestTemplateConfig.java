package com.ac.reserve.common.config.connect;

import com.ac.reserve.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        builder.setConnectTimeout(Duration.ofMillis(15000));
        builder.setReadTimeout(Duration.ofMillis(5000));
        RestTemplate restTemplate = builder.build();
        restTemplate.setErrorHandler(wechatResponseErrorHandler());
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());

        return  restTemplate;
    }


    public ResponseErrorHandler wechatResponseErrorHandler() {
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return response.getStatusCode().value() != 200;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                switch (response.getRawStatusCode()){
                    case 40029:
                        throw new ServiceException("invalid code.");
                    default:
                        throw new ServiceException("request failed.");
                }
            }
        };
    }
}
