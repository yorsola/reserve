package com.ac.reserve.web.api.service;

public interface WeChatService {

    /**
     * 登录，根据code 去微信做登录功能
     *
     * @param code
     * @return 返回会话信息
     */
    String getSessionInfo(String code);

}
