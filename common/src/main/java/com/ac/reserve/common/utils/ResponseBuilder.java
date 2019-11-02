package com.ac.reserve.common.utils;

import com.ac.reserve.common.constant.ResponseEnum;
import com.ac.reserve.common.domain.BaseResponse;

/**
 * @ClassName ResponseBuilder
 * @Description 响应构建工具
 * @Author zhuangding
 * @Date 2019/11/02 11:28
 **/
public class ResponseBuilder {
    public static <T> BaseResponse buildSuccess(T data) {
        return new BaseResponse<T>(ResponseEnum.SUCCESS, data);
    }

    public static BaseResponse buildSuccess() {
        return new BaseResponse(ResponseEnum.SUCCESS);
    }

    public static BaseResponse buildSuccess(String msg) {
        return new BaseResponse(ResponseEnum.SUCCESS.getCode(), msg);
    }

    public static BaseResponse buildSuccess(String code, String msg) {
        return new BaseResponse(code, msg);
    }

    public static <T> BaseResponse buildSuccess(String code, String msg, T data) {
        return new BaseResponse<T>(code, msg, data);
    }

    public static BaseResponse buildSuccess(ResponseEnum ResponseEnum) {
        return new BaseResponse(ResponseEnum);
    }

    public static <T> BaseResponse buildError(T data) {
        return new BaseResponse<T>(ResponseEnum.SERVER_INTERNAL_ERROR, data);
    }

    public static BaseResponse buildError() {
        return new BaseResponse(ResponseEnum.SERVER_INTERNAL_ERROR);
    }

    public static BaseResponse buildError(String msg) {
        return new BaseResponse(ResponseEnum.SERVER_INTERNAL_ERROR.getCode(), msg);
    }

    public static BaseResponse buildError(String code, String msg) {
        return new BaseResponse(code, msg);
    }

    public static <T> BaseResponse buildError(String code, String msg, T data) {
        return new BaseResponse<T>(code, msg, data);
    }

    public static BaseResponse buildError(ResponseEnum ResponseEnum) {
        return new BaseResponse(ResponseEnum);
    }
}
