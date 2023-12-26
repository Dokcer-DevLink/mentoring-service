package com.goorm.devlink.mentoringservice.vo.request;

import java.time.LocalDateTime;

public class ScheduleCreateRequest {

    private String mentoringUuid;
    private LocalDateTime startTime;
    private int unitTimeCount;
}
