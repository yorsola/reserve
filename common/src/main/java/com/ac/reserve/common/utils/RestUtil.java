package com.ac.reserve.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@Slf4j
public class RestUtil {

    @Autowired
    private RestTemplate restTemplate;

    public String httpPost(String requestUrl, Object requestBody) {
        log.debug(String.format("call httpPost{requestUrl=%s,requestBody=%s}", requestUrl, requestBody));
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestUrl, new HttpEntity(requestBody, new HttpHeaders(){{
                setContentType(MediaType.APPLICATION_JSON);
                set(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString());
            }}), String.class);
            return null == responseEntity ? null : responseEntity.getBody();
        } catch (RestClientException e) {
            log.debug(String.format("call httpPost error, requestUrl:{%s} requestBody:{%s}, cause:{%s}", requestUrl, requestBody, e.getMessage()), e);
        }
        return null;
    }


    public String httpPost(String requestUrl, Map<String, String> requestHeader, Object requestBody) {
        log.debug(String.format("call httpPost{requestUrl=%s,requestBody=%s}", requestUrl, requestBody));
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestUrl, new HttpEntity(requestBody, new HttpHeaders(){{
                setContentType(MediaType.APPLICATION_JSON);
                set(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString());
                if(requestHeader != null && requestHeader.size() > 0){
                    requestHeader.forEach((k,v)->{
                        set(k, v);
                    });
                }
            }}), String.class);
            return null == responseEntity ? null : responseEntity.getBody();
        } catch (RestClientException e) {
            log.debug(String.format("call httpPost error, requestUrl:{%s} requestBody:{%s}, cause:{%s}", requestUrl, requestBody, e.getMessage()), e);
        }
        return null;
    }





    public String httpPost(String requestUrl, Map<String, String> requestBody) {
        log.debug(String.format("call httpPost{requestUrl=%s,requestBody=%s}", requestUrl, JSONObject.toJSONString(requestBody)));
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestUrl, new HttpEntity(new LinkedMultiValueMap<String, String>(requestBody.size()) {{
                requestBody.forEach((String key, String value) -> {
                    add(key, value);
                });
            }}, new HttpHeaders(){{
                setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                set(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString());
            }}), String.class);
            return null == responseEntity ? null : responseEntity.getBody();
        } catch (RestClientException e) {
            log.debug(String.format("call httpPost error, requestUrl:{%s} requestBody:{%s}, cause:{%s}", requestUrl, JSONObject.toJSONString(requestBody), e.getMessage()), e);
        }
        return null;
    }

    public String httpGet(String requestUrl) {
        log.debug(String.format("call httpGet{requestUrl=%s}", requestUrl));
        try {

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUrl, String.class);
            return null == responseEntity ? null : responseEntity.getBody();
        } catch (RestClientException e) {
            log.debug(String.format("call httpGet error, requestUrl:{%s}, cause:{%s}", requestUrl, e.getMessage()), e);
        }
        return null;
    }

    public String httpGet(String requestUrl, Map<String, String> requestBody) {
        log.debug(String.format("call httpGet{requestUrl=%s,requestBody=%s}", requestUrl, JSONObject.toJSONString(requestBody)));
        System.out.println(requestUrl);
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUrl, String.class, requestBody);
            return null == responseEntity ? null : responseEntity.getBody();
        } catch (RestClientException e) {
            log.debug(String.format("call httpGet error, requestUrl:{%s} requestBody:{%s}, cause:{%s}", requestUrl, JSONObject.toJSONString(requestBody), e.getMessage()), e);
        }
        return null;
    }
}

