package com.goorm.devlink.mentoringservice.vo.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplyMessageResponse {
    private String applyUuid;
    private String message;

    public static ApplyMessageResponse getInstance(String applyUuid, String message){
        return ApplyMessageResponse.builder()
                .applyUuid(applyUuid)
                .message(message)
                .build();
    }
}
