package com.goorm.devlink.mentoringservice.entity;


import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import com.goorm.devlink.mentoringservice.vo.OnOffline;
import com.goorm.devlink.mentoringservice.vo.TargetType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Mentoring extends BaseTimeEntity{
    @Id
    @GeneratedValue
    Long id;
    @Column(name = "mentoring_uuid", unique=true)
    private String mentoringUuid;
    @Column(name = "mentor_uuid")
    private String mentorUuid;
    @Column(name = "mentee_uuid")
    private String menteeUuid;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "mentoring_place")
    private String mentoringPlace;
    @Column(name = "post_uuid")
    private String postUuid;
    @Column(name ="status")
    @Enumerated(EnumType.STRING)
    private MentoringStatus status;
    @Column(name = "on_offline")
    @Enumerated(EnumType.STRING)
    private OnOffline onOffline;


    public static Mentoring convertToMentoring(MentoringApply mentoringApply){
        String mentorUuidValue = mentoringApply.getTargetUuid();
        String menteeUuidValue = mentoringApply.getFromUuid();

        if(mentoringApply.getTargetType().equals(TargetType.MENTEE)){
            mentorUuidValue = mentoringApply.getFromUuid();
            menteeUuidValue = mentoringApply.getTargetUuid();
        }
        return Mentoring.builder()
                .mentoringUuid(UUID.randomUUID().toString())
                .mentorUuid(mentorUuidValue)
                .menteeUuid(menteeUuidValue)
                .startTime(mentoringApply.getStartTime())
                .endTime(mentoringApply.getEndTime())
                .mentoringPlace(mentoringApply.getMentoringPlace())
                .postUuid(mentoringApply.getPostUuid())
                .status(MentoringStatus.ONGOING)
                .onOffline(mentoringApply.getOnOffline())
                .build();

    }
}
