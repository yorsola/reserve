package com.ac.reserve.web.api.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String openid;
    private String accessToken;
    private Long created;
    private Long updated;
}
