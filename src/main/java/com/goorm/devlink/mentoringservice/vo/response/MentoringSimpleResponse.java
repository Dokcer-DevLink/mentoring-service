package com.goorm.devlink.mentoringservice.vo.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MentoringSimpleResponse {
    private String mentoringUuid;

    public static MentoringSimpleResponse getInstance(String mentoringUuid){
        return MentoringSimpleResponse.builder()
                .mentoringUuid(mentoringUuid)
                .build();
    }
}
