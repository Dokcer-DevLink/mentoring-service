package com.goorm.devlink.mentoringservice.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MentoringSimpleResponse {
    private String mentoringUuid;
    private String message;

    public static MentoringSimpleResponse getApplyInstance(String mentoringUuid){
        return MentoringSimpleResponse.builder()
                .mentoringUuid(mentoringUuid)
                .message("멘토링 신청이 완료되었습니다.")
                .build();
    }
}
