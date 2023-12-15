package com.goorm.devlink.mentoringservice.dto;


import com.goorm.devlink.mentoringservice.vo.MentoringApplyRequest;
import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import com.goorm.devlink.mentoringservice.vo.OnOffline;
import com.goorm.devlink.mentoringservice.vo.TargetType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TargetType targetType;
    private MentoringStatus mentoringStatus;

    public static MentoringApplyDto getInstance(MentoringApplyRequest mentoringApplyRequest, String userUuid){

        String mentorUuidInput = mentoringApplyRequest.getTargetUuid();
        String menteeUuidInput = userUuid;

        if(TargetType.MENTEE.equals(mentoringApplyRequest.getTargetType())){
            mentorUuidInput = userUuid;
            menteeUuidInput = mentoringApplyRequest.getTargetUuid();
        }

        return MentoringApplyDto.builder()
                .postUuid(mentoringApplyRequest.getPostUuid())
                .mentoringPlace(mentoringApplyRequest.getMentoringPlace())
                .onOffline(mentoringApplyRequest.getOnOffline())
                .startTime(mentoringApplyRequest.getStartTime())
                .endTime(mentoringApplyRequest.getEndTime())
                .targetType(mentoringApplyRequest.getTargetType())
                .mentorUuid(mentorUuidInput)
                .menteeUuid(menteeUuidInput)
                .mentoringUuid(UUID.randomUUID().toString())
                .mentoringStatus(MentoringStatus.WAITING)
                .build();

    }
}
