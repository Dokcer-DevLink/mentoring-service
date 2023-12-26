package com.goorm.devlink.mentoringservice.vo.response;

import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import com.goorm.devlink.mentoringservice.vo.OnOffline;
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
    private int unitTimeCount;
    private String recordContent;


}
