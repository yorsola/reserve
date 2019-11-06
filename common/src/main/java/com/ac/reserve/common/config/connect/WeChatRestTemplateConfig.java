package com.ac.reserve.common.config.connect;

import com.ac.reserve.common.dto.docket.DocketResponseDTO;
import com.ac.reserve.common.dto.wechat.CommonResponseDTO;
import com.ac.reserve.common.exception.ServiceException;
import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.util.JSONUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

@Configuration
public class WeChatRestTemplateConfig extends BaseRestTemplateConfig {


    @Bean
    public RestTemplate weChatRestTemplate() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
        HttpClient httpClient = httpClientBuilder.setConnectionManager(getConnectionManager()).build();

        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory(httpClient));
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        restTemplate.setInterceptors(Collections.singletonList(new AgentInterceptor()));
        restTemplate.setErrorHandler(wechatResponseErrorHandler());
        return restTemplate;
    }


    public ResponseErrorHandler wechatResponseErrorHandler() {
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                if (response.getStatusCode() == HttpStatus.OK) {
                    return false;
                }
                return true;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                String respErrorString = StreamUtils.copyToString(response.getBody(), Charset.forName("UTF-8"));
                CommonResponseDTO commonResponseDTO = null;
                try {
                    commonResponseDTO = (CommonResponseDTO) JSONUtil.readJson(respErrorString, CommonResponseDTO.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (commonResponseDTO != null) {
                    switch (commonResponseDTO.getErrcode()) {
                        case 40029:
                            throw new ServiceException(commonResponseDTO.getErrmsg());
                        default:
                            throw new ServiceException(commonResponseDTO.getErrmsg());
                    }
                }

            }
        };
    }
}
