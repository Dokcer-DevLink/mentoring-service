package com.goorm.devlink.mentoringservice.vo.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MentoringMessageResponse {
    private String mentoringUuid;
    private String message;

    public static MentoringMessageResponse getInstance(String mentoringUuid, String message){
        return MentoringMessageResponse.builder()
                .mentoringUuid(mentoringUuid)
                .message(message)
                .build();
    }
}
