package com.example.saju.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SajuExceptionHandler {
    @ExceptionHandler(SajuException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(SajuException e) {
        SajuErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }
}
