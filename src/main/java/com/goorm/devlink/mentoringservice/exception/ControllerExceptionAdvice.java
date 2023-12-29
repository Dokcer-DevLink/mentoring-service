package com.goorm.devlink.mentoringservice.exception;


import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "com.goorm.devlink.mentoringservice.controller")
public class ControllerExceptionAdvice {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResult> missingRequestHeaderExceptionHandler(MissingRequestHeaderException exception,
                                                                            HttpServletRequest request){
        return ResponseEntity.badRequest()
                .body(ErrorResult.getInstance(exception.getMessage(),request.getRequestURL().toString()));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResult> noSuchElementExceptionHandler(NoSuchElementException exception,
                                                                     HttpServletRequest request){
        return ResponseEntity.badRequest()
                .body(ErrorResult.getInstance(exception.getMessage(),request.getRequestURL().toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> methodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                       HttpServletRequest request){
        return ResponseEntity.badRequest().body(ErrorResult.getInstance(getMethodArgumentNotValidMessage(exception),
                request.getRequestURL().toString()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResult> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception,
                                                                              HttpServletRequest request){
        return ResponseEntity.badRequest()
                .body(ErrorResult.getInstance(exception.getMessage(),request.getRequestURL().toString()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResult> argumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception,
                                                                                  HttpServletRequest request){
        return ResponseEntity.badRequest()
                .body(ErrorResult.getInstance(exception.getMessage(),request.getRequestURL().toString()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResult> illegalArgumentExceptionHandler(IllegalArgumentException exception, HttpServletRequest request){
        return ResponseEntity.badRequest()
                .body(ErrorResult.getInstance(exception.getMessage(),request.getRequestURL().toString()));
    }


    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResult> feignExceptionHandler(FeignException exception,HttpServletRequest request){
        return ResponseEntity.internalServerError()
                .body(ErrorResult.getInstance(exception.getMessage(),request.getRequestURL().toString()));
    }

    private List<String> getMethodArgumentNotValidMessage(MethodArgumentNotValidException ex){
        ArrayList<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add("[" + fieldError.getField() +"]는(은) " + fieldError.getDefaultMessage() +
                    " [ 입력된 값 : " + fieldError.getRejectedValue() + " ]");
        }
        return errorMessages;
    }
}
