package com.ac.reserve.common.config.handler;

import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.exception.ServiceException;
import com.ac.reserve.common.utils.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


//@ControllerAdvice(basePackages = "com.ac.reserve.web.api.controller")
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
}
