package com.ac.reserve.web.api.dto;

import lombok.Data;

@Data
public class BillPdfInfoDTO {
    private String possessorNumber;
    private String possessorName;
    private String possessorPhone;
    private String code;
    // 0=成人，1=儿童
    private Integer type;
    // 审核状态，1-审核成功，2-审核拒绝，0-审核中
    private Integer state;
    private String roundIfo;
}
