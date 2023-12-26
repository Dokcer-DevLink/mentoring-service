package com.goorm.devlink.mentoringservice.dto;


import com.goorm.devlink.mentoringservice.vo.request.MentoringApplyRequest;
import com.goorm.devlink.mentoringservice.vo.MentoringApplyStatus;
import com.goorm.devlink.mentoringservice.vo.MentoringType;
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
    private MentoringType targetType;
    private String postUuid;
    private String mentoringPlace;
    private OnOffline onOffline;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private MentoringApplyStatus status;

    public static MentoringApplyDto getInstance(MentoringApplyRequest mentoringApplyRequest, String userUuid){

        return MentoringApplyDto.builder()
                .postUuid(mentoringApplyRequest.getPostUuid())
                .mentoringPlace(mentoringApplyRequest.getMentoringPlace())
                .onOffline(mentoringApplyRequest.getOnOffline())
                .startTime(mentoringApplyRequest.getStartTime())
                .endTime(mentoringApplyRequest.getEndTime())
                .targetType(mentoringApplyRequest.getTargetType())
                .status(MentoringApplyStatus.WAITING)
                .fromUuid(userUuid)
                .targetUuid(mentoringApplyRequest.getTargetUuid())
                .applyUuid(UUID.randomUUID().toString())
                .build();

    }
}
