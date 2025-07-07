package com.example.footballclub.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;

    public AppException setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}
