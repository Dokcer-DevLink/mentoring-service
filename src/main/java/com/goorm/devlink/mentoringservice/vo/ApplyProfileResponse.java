package com.goorm.devlink.mentoringservice.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyProfileResponse {

    private String fromUuid;

    public ApplyProfileResponse(String fromUuid) {
        this.fromUuid = fromUuid;
    }

    public static ApplyProfileResponse getInstance(String fromUuid) {
        return new ApplyProfileResponse(fromUuid);
    }
}
