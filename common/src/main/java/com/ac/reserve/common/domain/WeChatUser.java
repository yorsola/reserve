package com.ac.reserve.common.domain;

import lombok.Data;

@Data
public class WeChatUser {
    private String openid;
    private String sessionKey;
}
