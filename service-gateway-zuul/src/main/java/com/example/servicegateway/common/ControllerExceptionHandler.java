package com.example.servicegateway.common;

import lombok.extern.slf4j.Slf4j;
import org.example.common.common.ErrorResponse;
import org.example.common.common.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler({MyException.class})
    public ResponseEntity<ErrorResponse> handleException(MyException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        if(e instanceof MethodArgumentTypeMismatchException || e instanceof HttpMessageNotReadableException){
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

}

