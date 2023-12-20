package com.goorm.devlink.mentoringservice.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecordResponse {

    private String mentoringUuid;
    private String content;

    public static RecordResponse getInstance(String mentoringUuid, String content){
        return RecordResponse.builder()
                .mentoringUuid(mentoringUuid)
                .content(content)
                .build();
    }
}
