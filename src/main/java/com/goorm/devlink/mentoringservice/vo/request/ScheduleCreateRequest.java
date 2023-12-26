package com.goorm.devlink.mentoringservice.vo.request;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Getter
public class ScheduleCreateRequest {

    private String mentoringUuid;
    private LocalDateTime startTime;
    private int unitTimeCount;

    public static ScheduleCreateRequest getInstance(MentoringApply mentoringApply){
        return ScheduleCreateRequest.builder()
                .mentoringUuid(UUID.randomUUID().toString())
                .startTime(mentoringApply.getStartTime())
                .unitTimeCount(mentoringApply.getUnitTimeCount())
                .build();
    }
}
