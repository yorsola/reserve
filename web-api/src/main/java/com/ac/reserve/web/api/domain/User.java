package com.ac.reserve.web.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 8719165062207274522L;
    private Long id;

    private String openId;

    private String accessToken;

    private Date created;

    private Date updateed;

    private String sessionKey;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private Integer gender;

    private String nickName;

}
