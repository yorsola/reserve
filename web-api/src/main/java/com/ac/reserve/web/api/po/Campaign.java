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
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动 id
     */
    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "活动 id")
    private Long id;
    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String campaignName;
    /**
     * 活动地址
     */
    @ApiModelProperty(value = "活动地址")
    private String campaignLocation;
    /**
     * 活动图片
     */
    @ApiModelProperty(value = "活动图片")
    private String campaignImg;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String latitude;
    /**
     * 活动时间
     */
    @ApiModelProperty(value = "活动时间")
    private LocalDateTime campaignTime;
    /**
     * 活动详情
     */
    @ApiModelProperty(value = "活动详情")
    private String campaignDetails;
    /**
     * 参与须知
     */
    @ApiModelProperty(value = "参与须知")
    private String campaignGuidelines;
    /**
     * 常见问答
     */
    @ApiModelProperty(value = "常见问答")
    private String campaignProblems;
    /**
     * 有效性，1-存在，0-不存在
     */
    @ApiModelProperty(value = "有效性，1-存在，0-不存在")
    private Integer valid;



}
