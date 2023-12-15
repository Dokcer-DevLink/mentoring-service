package com.goorm.devlink.mentoringservice.vo;


import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MentoringApplyRequest {

    private String postUuid;
    private String mentoringPlace;
    private OnOffline onOffline;
    private LocalDate startTime;
    private LocalDate endTime;

}
