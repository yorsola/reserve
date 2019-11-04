package com.ac.reserve.common.dto.wechat;

import lombok.Data;

@Data
public class CommonResponseDTO {
    private Integer errcode;
    private String errmsg;

    public boolean valid() {
        return errcode == null || errcode == 0;
    }
}
