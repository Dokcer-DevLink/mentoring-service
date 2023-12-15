package com.goorm.devlink.mentoringservice.entity;


import com.goorm.devlink.mentoringservice.vo.MentoringApplyRequest;
import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentoring extends BaseTimeEntity {

    @Id
    @GeneratedValue
    Long id;
    @Column(name = "mentoring_uuid")
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
    private String menToringPlace;
    @Column(name ="status")
    private MentoringStatus status;
    @Column(name = "post_uuid")
    private String postUuid;
    @Column(name = "on_offline")
    private String onOffline;



}
