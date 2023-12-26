package com.goorm.devlink.mentoringservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "com.goorm.devlink.mentoringservice.service")
public class ServiceExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResult> runtimeExceptionHandler(RuntimeException exception,
                                                               HttpServletRequest request){

        return ResponseEntity.internalServerError()
                .body(ErrorResult.getInstance(exception.getMessage(), request.getRequestURI().toString()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResult> noSuchElementExceptionHandler(NoSuchElementException exception,
                                                                     HttpServletRequest request){

        return ResponseEntity.internalServerError()
                .body(ErrorResult.getInstance(exception.getMessage(), request.getRequestURI().toString()));
    }

}
