package com.ac.reserve.web.api.dto;

import lombok.Data;

@Data
public class CheckExaminePostResponseDTO {

    /**
     * 证件类型编码
     */
    private String ztype;
    /**
     * 证件类型名称
     */
    private String ztypename;
    /**
     * 姓名
     */
    private String zname;
    /**
     * 证件号码
     */
    private String znumber;
    /**
     * 打印单位
     */
    private String dydw;
    /**
     * 备审ID
     */
    private String bsId;
    /**
     * 创建时间
     */
    private String hlwcjsj;
    /**
     * 背审时间
     */
    private String gawcjsj;
    /**
     * 背审状态[1:已背审,0:未背审]
     */
    private String zt;
    /**
     * 背审结果状态[1:背审通过,0:背审不通过]
     */
    private String zzbs;
    /**
     * 背审结果描述
     */
    private String des;

}
