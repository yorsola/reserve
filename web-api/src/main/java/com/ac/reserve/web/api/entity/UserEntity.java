package com.ac.reserve.web.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2019-11-04
 */
@Data
@Accessors(chain = true)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
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
	private String accessToken;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
	private LocalDate created;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
	private LocalDate updated;
    /**
     * 有效性
     */
    @ApiModelProperty(value = "有效性")
	private Integer valid;



}
