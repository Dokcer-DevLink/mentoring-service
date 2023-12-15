package com.goorm.devlink.mentoringservice.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MentoringMessageResponse {
    private String mentoringUuid;
    private String message;

    public static MentoringMessageResponse getApplyInstance(String mentoringUuid){
        return MentoringMessageResponse.builder()
                .mentoringUuid(mentoringUuid)
                .message("멘토링 신청이 완료되었습니다.")
                .build();
    }
}
