package com.ac.reserve.common.config.connect;

import com.ac.reserve.common.exception.ServiceException;
import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.util.JSONUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class ReserveRestTemplateConfig extends BaseRestTemplateConfig {


    @Bean
    public RestTemplate reserveRestTemplate() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
        HttpClient httpClient = httpClientBuilder.setConnectionManager(getConnectionManager()).build();

        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory(httpClient));
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        restTemplate.setInterceptors(Collections.singletonList(new AgentInterceptor()));
        restTemplate.setErrorHandler(responseErrorHandler);
        restTemplate.setErrorHandler(wechatResponseErrorHandler());
        return restTemplate;
    }

    ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
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
            BaseResponse baseResponse = null;
            try {
                baseResponse = (BaseResponse) JSONUtil.readJson(respErrorString, BaseResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (baseResponse != null) {
                switch (baseResponse.getCode()) {
                    case "0":
                        throw new ServiceException(baseResponse.getMsg());
                    default:
                        throw new ServiceException(baseResponse.getMsg());
                }
            }

        }
    };


    public class AgentInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            List<Charset> charsets = new ArrayList<>();
            charsets.add(Charset.forName("UTF-8"));
            headers.setAcceptCharset(charsets);

            return execution.execute(request, body);
        }
    }

    public ResponseErrorHandler wechatResponseErrorHandler() {
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return response.getStatusCode().value() != 200;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                switch (response.getRawStatusCode()) {
                    case 40029:
                        throw new ServiceException("invalid code.");
                    default:
                        throw new ServiceException("request failed.");
                }
            }
        };
    }
}
