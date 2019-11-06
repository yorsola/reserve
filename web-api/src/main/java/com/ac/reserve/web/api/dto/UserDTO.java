package com.ac.reserve.web.api.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String openid;
    private String accesstoken;
    private Long created;
    private Long updated;
    private Integer valid;
}
