package com.ac.reserve.common.exception;


public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
