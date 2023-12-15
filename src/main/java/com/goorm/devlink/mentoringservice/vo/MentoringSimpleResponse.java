package com.goorm.devlink.mentoringservice.vo;

import lombok.Builder;

@Builder
public class MentoringSimpleResponse {

    private String message;
    private String mentoringUuid;

    public static MentoringSimpleResponse getApplyInstance(String mentoringUuid){
        return MentoringSimpleResponse.
                builder()
                .mentoringUuid(mentoringUuid)
                .message("멘토링 신청이 완료되었습니다.")
                .build();
    }
}
