package com.operation.timeout.observer;

public class OperationTimeOutObserver {
    private Exception exception;
    private String exceptionType;
    private Boolean exceptionOccured;
    private Boolean timeOut;
    private Boolean success;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public Boolean getExceptionOccured() {
        return exceptionOccured;
    }

    public void setExceptionOccured(Boolean exceptionOccured) {
        this.exceptionOccured = exceptionOccured;
    }

    public Boolean getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Boolean timeOut) {
        this.timeOut = timeOut;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void cleanObserver(){
        this.exception = null;
        this.exceptionType = null;
        this.exceptionOccured = false;
        this.success = false;
        this.timeOut = false;
    }
}
