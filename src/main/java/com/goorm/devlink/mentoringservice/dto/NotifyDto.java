package com.goorm.devlink.mentoringservice.dto;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;


@Builder
@Getter
public class NotifyDto implements Serializable {

    public enum NotifyType{
        MENTORING_APPLY, MENTORING_ACCEPT, MENTORING_REJECT;
    }


    private String notificationUuid;
    private String senderUuid;
    private String recipientUuid;
    private String applyUuid;
    private String postUuid;
    private NotifyType notifyType;

    public static NotifyDto getInstanceApply(MentoringApply mentoringApply){
        return NotifyDto.builder()
                .notificationUuid(UUID.randomUUID().toString())
                .applyUuid(mentoringApply.getApplyUuid())
                .postUuid(mentoringApply.getPostUuid())
                .senderUuid(mentoringApply.getFromUuid())
                .recipientUuid(mentoringApply.getTargetUuid())
                .notifyType(NotifyType.MENTORING_APPLY)
                .build();
    }

    public static NotifyDto getInstanceAccept(MentoringApply mentoringApply){
        return NotifyDto.builder()
                .notificationUuid(UUID.randomUUID().toString())
                .applyUuid(mentoringApply.getApplyUuid())
                .postUuid(mentoringApply.getPostUuid())
                .senderUuid(mentoringApply.getTargetUuid())
                .recipientUuid(mentoringApply.getFromUuid())
                .notifyType(NotifyType.MENTORING_ACCEPT)
                .build();
    }

    public static NotifyDto getInstanceReject(MentoringApply mentoringApply){
        return NotifyDto.builder()
                .notificationUuid(UUID.randomUUID().toString())
                .applyUuid(mentoringApply.getApplyUuid())
                .postUuid(mentoringApply.getPostUuid())
                .senderUuid(mentoringApply.getTargetUuid())
                .recipientUuid(mentoringApply.getFromUuid())
                .notifyType(NotifyType.MENTORING_REJECT)
                .build();
    }

}
