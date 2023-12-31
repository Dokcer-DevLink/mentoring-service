package com.goorm.devlink.mentoringservice.vo.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplySimpleResponse {
    private String applyUuid;

    public static ApplySimpleResponse getInstance(String applyUuid){
        return ApplySimpleResponse.builder()
                .applyUuid(applyUuid)
                .build();
    }
}
