package com.ac.reserve.web.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class BillRequestDTO {

    /**
     * 证件类型：111=二代居民身份证，411=护照，990=其他，991=港澳居民来往内地通行证，992=香港永久性居民身份证，993台胞证
     */
    @NotBlank(message = "证件类型")
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
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "使用人姓名")
    private String possessorName;
    /**
     * 使用人电话
     */
    @NotBlank(message = "电话不能为空")
    @ApiModelProperty(value = "使用人电话")
    private String possessorPhone;

    @ApiModelProperty(value = "使用人头像")
    private String possessorImg;
    /**
     * 场次
     */
    @NotNull(message = "场次不能为空")
    @ApiModelProperty(value = "场次 id," +
            "1-A区入口：昌盛路、粤海东路，" +
            "2-B区入口：粤海东路、联安路，" +
            "3-C区入口：联安路、水湾路，" +
            "4-D区入口：水湾路，" +
            "5-E区入口：情侣南路延长线")
    private Integer roundId;
    /**
     * 电子票类型，0-成人电子票，1-儿童电子票
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "电子票类型，0-成人电子票，1-儿童电子票")
    private Integer type;

}
