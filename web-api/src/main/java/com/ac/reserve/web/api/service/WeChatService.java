package com.ac.reserve.web.api.service;

import com.ac.reserve.web.api.po.User;

public interface WeChatService {

    /**
     * 登录，根据code 去微信做登录功能
     * @param code
     * @return 返回自定义的登录token，放在Redis中
     */
    String login(String code);

}
