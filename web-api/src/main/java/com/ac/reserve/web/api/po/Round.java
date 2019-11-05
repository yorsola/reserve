package com.ac.reserve.web.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@Accessors(chain = true)
public class Round implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;
    /**
     * 场次信息
     */
    @ApiModelProperty(value = "场次信息")
    private String roundInfo;
    /**
     * 活动 id
     */
    @ApiModelProperty(value = "活动 id")
    private Long campaignId;


}
