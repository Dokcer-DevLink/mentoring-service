package com.goorm.devlink.mentoringservice.dto;

import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import com.goorm.devlink.mentoringservice.vo.request.MentoringStatusRequest;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MentoringStatusDto {

    private String userUuid;
    private String mentoringUuid;
    private MentoringStatus mentoringStatus;

    public static MentoringStatusDto getInstance(MentoringStatusRequest request, String userUuid){
        return MentoringStatusDto.builder()
                .userUuid(userUuid)
                .mentoringStatus(request.getStatus())
                .mentoringUuid(request.getMentoringUuid())
                .build();
    }
}
