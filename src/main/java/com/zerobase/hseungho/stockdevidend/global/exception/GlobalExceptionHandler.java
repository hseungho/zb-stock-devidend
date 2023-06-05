package com.zerobase.hseungho.stockdevidend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<?> handleCustomException(AbstractException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ErrorResponse.builder()
                        .code(e.getStatusCode())
                        .message(e.getMessage())
                        .build());
    }

}
