package com.ac.reserve.common.dto.wechat;

import lombok.Data;

@Data
public class LoginResponseDTO extends CommonResponseDTO {
    private String openid;
    private String session_key;
    private String unionid;

}
