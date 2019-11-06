package com.ac.reserve.web.api.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModelProperty;


@Data
@Builder
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;
    /**
     * openid
     */
    @ApiModelProperty(value = "openid")
    private String openid;
    /**
     * 会话密钥
     */
    @ApiModelProperty(value = "会话密钥")
    private String sessionKey;
    /**
     * 访问令牌
     */
    @ApiModelProperty(value = "访问令牌")
    private String accesstoken;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created", fill = FieldFill.INSERT)
    private LocalDateTime created;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(value = "updated", fill = FieldFill.UPDATE)
    private LocalDateTime updated;
    /**
     * 有效性,1-存在，0-不存在
     */
    @ApiModelProperty(value = "有效性,1-存在，0-不存在")
    private Integer valid;


}
