
package com.ac.reserve.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    /**
     * Extract request header
     *
     * @param req
     * http request
     * @return
     */
    public static String getHeader(HttpServletRequest req, String name) {
        return req.getHeader(name);
    }

    public static Map<String, String> getHeadersInfoMap(HttpServletRequest req) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = req.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    public static String printRequest(HttpRequest req, byte[] body) {

        StringBuilder sb = new StringBuilder();
        sb.append(req.getURI().toString()).append("\r\n");

        sb.append(req.getMethodValue().toUpperCase()).append(" ").append(req.getURI().getPath()).append(" ")
                .append(req.getURI().getScheme().toUpperCase()).append("\r\n").append(getHeaders(req)).append("\r\n")
                .append(new String(body, Charset.forName("UTF-8")));
        return sb.toString();
    }

    private static String getHeaders(HttpRequest req) {
        StringBuilder sb = new StringBuilder();
        HttpHeaders headers = req.getHeaders();
        headers.entrySet().forEach(entry -> {
            sb.append(entry.getKey()).append(": ");
            entry.getValue().forEach(item -> {
                sb.append(item).append(" ");
            });
            sb.append("\r\n");
        });

        return sb.toString();
    }

    /**
     * 获取Ip地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor!=null&&XFor.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":XFor;
    }

}
