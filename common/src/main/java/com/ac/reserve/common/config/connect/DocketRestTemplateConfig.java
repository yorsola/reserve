package com.ac.reserve.common.config.connect;

import com.ac.reserve.common.dto.docket.DocketResponseDTO;
import com.ac.reserve.common.exception.ServiceException;
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
public class DocketRestTemplateConfig extends BaseRestTemplateConfig {


    @Bean
    public RestTemplate docketRestTemplate() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
        HttpClient httpClient = httpClientBuilder.setConnectionManager(getConnectionManager()).build();

        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory(httpClient));
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        restTemplate.setInterceptors(Collections.singletonList(new AgentInterceptor()));
        restTemplate.setErrorHandler(responseErrorHandler);
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
            DocketResponseDTO docketResponseDTO = null;
            try {
                docketResponseDTO = (DocketResponseDTO) JSONUtil.readJson(respErrorString, DocketResponseDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (docketResponseDTO != null) {
                switch (docketResponseDTO.getCode()) {
                    case "0":
                        throw new ServiceException(docketResponseDTO.getMsg());
                    default:
                        throw new ServiceException(docketResponseDTO.getMsg());
                }
            }

        }
    };

}
