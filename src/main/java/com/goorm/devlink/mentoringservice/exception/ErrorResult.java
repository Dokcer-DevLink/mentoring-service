package com.goorm.devlink.mentoringservice.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ErrorResult {

    private String requestUrl;
    private LocalDateTime timestamp;
    private String errorMessage;
    private List<String> errorMessages;

    public static ErrorResult getInstance(String errorMessage, String requestUrl) {
        return ErrorResult.builder()
                .requestUrl(requestUrl)
                .errorMessage(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResult getInstance(List<String> errorMessages, String requestUrl) {
        return ErrorResult.builder()
                .requestUrl(requestUrl)
                .errorMessages(errorMessages)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
