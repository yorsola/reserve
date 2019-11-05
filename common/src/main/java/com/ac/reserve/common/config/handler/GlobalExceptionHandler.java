package com.ac.reserve.common.config.handler;

import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.exception.ServiceException;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;


@ControllerAdvice(basePackages = "com.ac.reserve.web.api.controller")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse handleException(Exception e){
        log.error(e.getMessage());
        return ResponseBuilder.buildError(e.getMessage());
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public BaseResponse handleRuntimeException(ServiceException e){
        log.error(e.getMessage());
        return ResponseBuilder.buildError(e.getMessage());
    }

    /**
     * 参数验证异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public BaseResponse dataInvalidExceptionHandler(Exception ex) {
        BindingResult bindingResult = null;
        if (ex instanceof BindException) {
            bindingResult = ((BindException) ex).getBindingResult();
        } else if (ex instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            arrayList.add(error.getDefaultMessage());
        }
        log.info("参数验证异常：{}", JSON.toJSONString(arrayList));
        return ResponseBuilder.buildError(arrayList.get(0));
    }
}
