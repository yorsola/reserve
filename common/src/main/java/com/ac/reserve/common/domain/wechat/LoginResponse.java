package com.ac.reserve.common.domain.wechat;

import lombok.Data;

@Data
public class LoginResponse extends CommonResponse {
    private String openid;
    private String session_key;
    private String unionid;

}
