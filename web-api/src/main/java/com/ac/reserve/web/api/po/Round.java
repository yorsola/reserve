package com.ac.reserve.web.api.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class Round implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private Long id;
    /**
     * 场次地址
     */
    @ApiModelProperty(value = "场次地址")
    private String roundLocation;
    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private Long campaignId;


}
