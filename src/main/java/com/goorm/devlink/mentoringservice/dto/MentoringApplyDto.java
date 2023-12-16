package com.goorm.devlink.mentoringservice.dto;


import com.goorm.devlink.mentoringservice.vo.MentoringApplyRequest;
import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import com.goorm.devlink.mentoringservice.vo.OnOffline;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class MentoringApplyDto {

    private String applyUuid;
    private String fromUuid;
    private String targetUuid;
    private String postUuid;
    private String mentoringPlace;
    private OnOffline onOffline;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private MentoringStatus status;

    public static MentoringApplyDto getInstance(MentoringApplyRequest mentoringApplyRequest, String userUuid){

        return MentoringApplyDto.builder()
                .postUuid(mentoringApplyRequest.getPostUuid())
                .mentoringPlace(mentoringApplyRequest.getMentoringPlace())
                .onOffline(mentoringApplyRequest.getOnOffline())
                .startTime(mentoringApplyRequest.getStartTime())
                .endTime(mentoringApplyRequest.getEndTime())
                .status(MentoringStatus.WAITING)
                .fromUuid(userUuid)
                .targetUuid(mentoringApplyRequest.getTargetUuid())
                .applyUuid(UUID.randomUUID().toString())
                .build();

    }
}
