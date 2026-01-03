package com.example.saju.exception;

import lombok.Getter;

@Getter
public class SajuException extends RuntimeException{
    private final SajuErrorCode errorCode;

    public SajuException(SajuErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public SajuException(SajuErrorCode errorCode, String extraMessage) {
        super(errorCode.getMessage()+" "+extraMessage);
        this.errorCode = errorCode;
    }
}
