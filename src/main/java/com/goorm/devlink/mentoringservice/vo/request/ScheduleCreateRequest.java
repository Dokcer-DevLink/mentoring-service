package com.goorm.devlink.mentoringservice.vo.request;

import com.goorm.devlink.mentoringservice.dto.ScheduleDto;
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

    public static ScheduleCreateRequest getInstance(ScheduleDto scheduleDto){
        return ScheduleCreateRequest.builder()
                .mentoringUuid(scheduleDto.getMentoringUuid())
                .startTime(scheduleDto.getStartTime())
                .unitTimeCount(scheduleDto.getUnitTimeCount())
                .build();
    }
}
