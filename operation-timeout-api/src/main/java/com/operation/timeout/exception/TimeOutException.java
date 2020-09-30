package com.operation.timeout.exception;

public class TimeOutException extends RuntimeException{
    private String erroCode;

    public TimeOutException(String errorMessage, String errorCode){
        super(errorMessage);
        this.erroCode = errorCode;
    }
}
