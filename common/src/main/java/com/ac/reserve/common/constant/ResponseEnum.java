package com.ac.reserve.common.constant;


public enum ResponseEnum {
    SUCCESS("0000", "Success"),
    SERVER_INTERNAL_ERROR("1000", "Server Internal Error"),
    BUSSINESS_LOGIC_ERROR("2000", "Business Logic Error");

    private String code;
    private String msg;

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
