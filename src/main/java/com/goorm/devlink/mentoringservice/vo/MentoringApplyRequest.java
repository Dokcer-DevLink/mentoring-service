package com.goorm.devlink.mentoringservice.vo;


import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class MentoringApplyRequest {

    private String postUuid;
    private String targetUuid;
    private MentoringType mentoringType;
    private String mentoringPlace;
    private OnOffline onOffline;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


}
