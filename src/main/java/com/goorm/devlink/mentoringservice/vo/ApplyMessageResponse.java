package com.goorm.devlink.mentoringservice.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplyMessageResponse {
    private String applyUuid;
    private String message;

    public static ApplyMessageResponse getApplyInstance(String applyUuid){
        return ApplyMessageResponse.builder()
                .applyUuid(applyUuid)
                .message("멘토링 신청이 완료되었습니다.")
                .build();
    }
}
