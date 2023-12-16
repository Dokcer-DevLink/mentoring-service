package com.goorm.devlink.mentoringservice.vo;

import com.goorm.devlink.mentoringservice.entity.Mentoring;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MentoringSimpleResponse {
    private String mentoringUuid;
    private String mentorUuid;
    private String menteeUuid;
    private String mentoringPlace;
    private String postUuid;
    private OnOffline onOffline;
    private MentoringStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


}
