package com.ac.reserve.web.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModelProperty;


@Data
@Accessors(chain = true)
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;
    /**
     * 用户 id
     */
    @ApiModelProperty(value = "用户 id")
    private Long userId;
    /**
     * 使用人证件号
     */
    @ApiModelProperty(value = "使用人证件号")
    private String
            possessorNumber;
    /**
     * 使用人名字
     */
    @ApiModelProperty(value = "使用人名字")
    private String possessorName;
    /**
     * 使用人电话
     */
    @ApiModelProperty(value = "使用人电话")
    private String possessorPhone;
    /**
     * 场次
     */
    @ApiModelProperty(value = "场次")
    private String round;
    /**
     * 入场序列码
     */
    @ApiModelProperty(value = "入场序列码")
    private String code;
    /**
     * 电子票类型，0-成人，1-儿童
     */
    @ApiModelProperty(value = "电子票类型，0-成人，1-儿童")
    private Integer type;
    /**
     * 审核状态，1-审核成功，2-审核拒绝，0-审核中
     */
    @ApiModelProperty(value = "审核状态，1-审核成功，2-审核拒绝，0-审核中")
    private Integer state;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime created;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updated;
    /**
     * 有效性
     */
    @ApiModelProperty(value = "有效性")
    private Integer valid;


}
