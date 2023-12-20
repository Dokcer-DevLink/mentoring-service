package com.goorm.devlink.mentoringservice.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MentoringDetailResponse {

    private String mentoringUuid;
    private String mentorUuid;
    private String menteeUuid;
    private String mentoringPlace;
    private String postUuid;
    private OnOffline onOffline;
    private MentoringStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String recordContent;


}
