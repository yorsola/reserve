package com.ac.reserve.web.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 申请备审请求对象
 */
@Data
@Builder
public class ExamineRequestDTO {
    @ApiModelProperty(value = "证件类型编码")
    private String ztype;
    @ApiModelProperty(value = "证件类型名称")
    private String ztypename;
    @ApiModelProperty(value = "姓名")
    private String zname;
    @ApiModelProperty(value = "证件号码")
    private String znumber;
    @ApiModelProperty(value = "所属单位")
    private String dydw;
}
