package com.ac.reserve.common.domain.wechat;

import lombok.Data;

@Data
public class CommonResponse {
    private Integer errcode;
    private String errmsg;

    public boolean valid() {
        return errcode == null || errcode == 0;
    }
}
