package com.goorm.devlink.mentoringservice.dto;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class ScheduleDto {

    private String mentoringUuid;
    private String userUuid;
    private LocalDateTime startTime;
    private int unitTimeCount;

    public static ScheduleDto getInstanceFrom(MentoringApply mentoringApply,String mentoringUuid){
        return ScheduleDto.builder()
                .mentoringUuid(mentoringUuid)
                .userUuid(mentoringApply.getFromUuid())
                .startTime(mentoringApply.getStartTime())
                .unitTimeCount(mentoringApply.getUnitTimeCount())
                .build();
    }

    public static ScheduleDto getInstanceTarget(MentoringApply mentoringApply,String mentoringUuid){
        return ScheduleDto.builder()
                .mentoringUuid(mentoringUuid)
                .userUuid(mentoringApply.getTargetUuid())
                .startTime(mentoringApply.getStartTime())
                .unitTimeCount(mentoringApply.getUnitTimeCount())
                .build();
    }

}
