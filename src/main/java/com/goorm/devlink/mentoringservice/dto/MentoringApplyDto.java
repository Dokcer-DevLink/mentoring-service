package com.goorm.devlink.mentoringservice.dto;


import com.goorm.devlink.mentoringservice.vo.MentoringApplyRequest;
import com.goorm.devlink.mentoringservice.vo.OnOffline;
import com.goorm.devlink.mentoringservice.vo.TargetType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
public class MentoringApplyDto {

    private String mentoringUuid;
    private String mentorUuid;
    private String menteeUuid;
    private String postUuid;
    private String mentoringPlace;
    private OnOffline onOffline;
    private LocalDate startTime;
    private LocalDate endTime;
    private TargetType targetType;

    public static MentoringApplyDto getInstance(MentoringApplyRequest mentoringApplyRequest, String userUuid){

        String mentorUuidInput = mentoringApplyRequest.getTargetUuid();
        String menteeUuidInput = userUuid;

        if(TargetType.MENTEE.equals(mentoringApplyRequest.getTargetType())){
            mentorUuidInput = userUuid;
            menteeUuidInput = mentoringApplyRequest.getTargetUuid();
        }

        return MentoringApplyDto.builder()
                .mentoringUuid(UUID.randomUUID().toString())
                .postUuid(mentoringApplyRequest.getPostUuid())
                .mentoringPlace(mentoringApplyRequest.getMentoringPlace())
                .onOffline(mentoringApplyRequest.getOnOffline())
                .startTime(mentoringApplyRequest.getStartTime())
                .endTime(mentoringApplyRequest.getEndTime())
                .targetType(mentoringApplyRequest.getTargetType())
                .mentorUuid(mentorUuidInput)
                .menteeUuid(menteeUuidInput)
                .build();

    }
}
