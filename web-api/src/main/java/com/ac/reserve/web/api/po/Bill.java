package com.ac.reserve.web.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
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
     * 证件类型，111-二代居民身份证，411-护照，990=其他，991=港澳居民往来内地通行证，992-香港永久性居民身份证，993-台胞证
     */
    @ApiModelProperty(value = "证件类型，111-二代居民身份证，411-护照，990=其他，991=港澳居民往来内地通行证，992-香港永久性居民身份证，993-台胞证")
    private String documentType;
    /**
     * 使用人证件号
     */
    @ApiModelProperty(value = "使用人证件号")
    private String possessorNumber;
    /**
     * 使用人名字
     */
    @ApiModelProperty(value = "使用人名字")
    private String possessorName;
    /**
     * 使用人头像
     */
    @ApiModelProperty(value = "使用人头像")
    private String possessorImg;
    /**
     * 使用人电话
     */
    @ApiModelProperty(value = "使用人电话")
    private String possessorPhone;
    /**
     * 场次 id
     */
    @ApiModelProperty(value = "场次 id")
    private Integer roundId;
    /**
     * 入场序列码值
     */
    @ApiModelProperty(value = "入场序列码值")
    private String code;
    /**
     * 电子票类型，0-成人电子票，1-儿童电子票
     */
    @ApiModelProperty(value = "电子票类型，0-成人电子票，1-儿童电子票")
    private Integer type;
    /**
     * 审核状态，1-审核成功，2-审核拒绝，0-审核中
     */
    @ApiModelProperty(value = "审核状态，1-审核成功，2-审核拒绝，0-审核中")
    private Integer state;
    /**
     * 备审 id
     */
    @ApiModelProperty(value = "备审 id")
    private String examineId;
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
     * 有效性,1-存在，0-不存在
     */
    @ApiModelProperty(value = "有效性,1-存在，0-不存在")
    private Integer valid;


}
