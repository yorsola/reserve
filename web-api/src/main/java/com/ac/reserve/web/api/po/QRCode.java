package com.ac.reserve.web.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class QRCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;
    /**
     * 使用人证件号
     */
    @ApiModelProperty(value = "使用人证件号")
    private String idCardNo;
    /**
     * 二维码键
     */
    @ApiModelProperty(value = "二维码键")
    private String tempKey;

    /**
     * 二维码内容
     */
    @ApiModelProperty(value = "二维码内容")
    private String tempValue;
    /**
     * 扫码时间
     */
    @ApiModelProperty(value = "扫码时间")
    private String scanTime;
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
