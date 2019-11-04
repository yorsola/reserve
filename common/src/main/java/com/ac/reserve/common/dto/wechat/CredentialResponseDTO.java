package com.ac.reserve.common.dto.wechat;

import lombok.Data;

@Data
public class CredentialResponseDTO extends CommonResponseDTO{
    private String access_token;
    private String expires_in;
}
