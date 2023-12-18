package com.goorm.devlink.mentoringservice.dto;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;


@Builder
@Getter
public class NotifyDto implements Serializable {

    public enum NotifyType{
        MENTORING_APPLY, MENTORING_ACCEPT, MENTORING_REJECT;
    }


    private String senderUuid;
    private String receiverUuid;
    private NotifyType notifyType;

    public static NotifyDto getInstance(MentoringApply mentoringApply,NotifyType notifyType){
        return NotifyDto.builder()
                .senderUuid(mentoringApply.getFromUuid())
                .receiverUuid(mentoringApply.getTargetUuid())
                .notifyType(notifyType)
                .build();
    }

}
