package com.ac.reserve.common.response;

import com.ac.reserve.common.constant.ResponseEnum;

import java.io.Serializable;

/**
 * @ClassName BaseResponse
 * @Description 统一的响应格式
 * @Author zhuangding
 * @Date 2019/11/02 11:24
 **/
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -1475769720865099686L;
    private String code;

    private String msg;

    private T data;


    public BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(ResponseEnum ResponseEnum) {
        this.code = ResponseEnum.getCode();
        this.msg = ResponseEnum.getMsg();
    }

    public BaseResponse(ResponseEnum ResponseEnum, T data) {
        this.code = ResponseEnum.getCode();
        this.msg = ResponseEnum.getMsg();
        this.data = data;
    }

    public BaseResponse() {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
