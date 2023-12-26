package com.goorm.devlink.mentoringservice.vo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyPostResponse {

    private String postUuid;

    public ApplyPostResponse(String postUuid) {
        this.postUuid = postUuid;
    }

    public static ApplyPostResponse getInstance(String postUuid){
        return new ApplyPostResponse(postUuid);
    }
}
