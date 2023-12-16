package com.goorm.devlink.mentoringservice.entity;


import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import com.goorm.devlink.mentoringservice.vo.OnOffline;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentoringApply extends BaseTimeEntity {

    @Id
    @GeneratedValue
    Long id;
    @Column(name = "apply_uuid", unique=true)
    private String applyUuid;
    @Column(name = "from_uuid")
    private String fromUuid;
    @Column(name = "target_uuid")
    private String targetUuid;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "mentoring_place")
    private String mentoringPlace;
    @Column(name ="status")
    @Enumerated(EnumType.STRING)
    private MentoringStatus status;
    @Column(name = "post_uuid")
    private String postUuid;
    @Column(name = "on_offline")
    @Enumerated(EnumType.STRING)
    private OnOffline onOffline;



}
