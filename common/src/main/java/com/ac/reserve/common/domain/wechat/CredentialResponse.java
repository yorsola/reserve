package com.ac.reserve.common.domain.wechat;

import lombok.Data;

@Data
public class CredentialResponse extends CommonResponse{
    private String access_token;
    private String expires_in;
}
