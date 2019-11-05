package com.ac.reserve.web.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class ReserveBillRequestDTO {

    /**
     * 用户 id
     */
    @NotNull(message = "用户 id 不能为空")
    @ApiModelProperty(value = "用户 id")
    private Long userId;
    /**
     * 证件类型：111=二代居民身份证，411=护照，990=其他，991=港澳居民来往内地通行证，992=香港永久性居民身份证，993台胞证
     */
    @NotNull(message = "证件类型")
    @ApiModelProperty(value = "证件类型：111=二代居民身份证，411=护照，990=其他，991=港澳居民来往内地通行证，992=香港永久性居民身份证，993台胞证")
    private String documentType;
    /**
     * 使用人证件号
     */
    @NotBlank(message = "证件号不能为空")
    @ApiModelProperty(value = "使用人证件号")
    private String possessorNumber;
    /**
     * 使用人名字
     */
    @NotBlank(message = "名字不能为空")
    @ApiModelProperty(value = "使用人名字")
    private String possessorName;
    /**
     * 使用人电话
     */
    @NotBlank(message = "电话不能为空")
    @ApiModelProperty(value = "使用人电话")
    private String possessorPhone;
    /**
     * 场次
     */
    @NotBlank(message = "场次不能为空")
    @ApiModelProperty(value = "场次")
    private String round;
    /**
     * 电子票类型，0-成人，1-儿童
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "电子票类型，0-成人，1-儿童")
    private Integer type;
    @NotBlank(message = "单位不能为空")
    @ApiModelProperty(value = "所属单位")
    private String companyName;
}
