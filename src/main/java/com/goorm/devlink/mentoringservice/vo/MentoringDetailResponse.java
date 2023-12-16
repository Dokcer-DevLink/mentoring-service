package com.goorm.devlink.mentoringservice.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MentoringDetailResponse {

    private String mentoringUuid;
    private Profile mentorProfile;
    private Profile menteeProfile;
    private String mentoringPlace;
    private String postUuid;
    private OnOffline onOffline;
    private MentoringApplyStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
