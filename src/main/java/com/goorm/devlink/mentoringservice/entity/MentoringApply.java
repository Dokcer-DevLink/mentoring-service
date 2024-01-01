package com.goorm.devlink.mentoringservice.entity;


import com.goorm.devlink.mentoringservice.vo.MentoringApplyStatus;
import com.goorm.devlink.mentoringservice.vo.MentoringType;
import com.goorm.devlink.mentoringservice.vo.OnOffline;
import com.goorm.devlink.mentoringservice.vo.response.ApplyPostResponse;
import com.goorm.devlink.mentoringservice.vo.response.MentoringPostResponse;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "unit_time_count")
    private int unitTimeCount;
    @Column(name = "mentoring_place")
    private String mentoringPlace;
    @Column(name = "post_uuid")
    private String postUuid;
    @Column(name ="status")
    @Enumerated(EnumType.STRING)
    private MentoringApplyStatus status;
    @Column(name = "target_type")
    @Enumerated(EnumType.STRING)
    private MentoringType targetType;
    @Column(name = "on_offline")
    @Enumerated(EnumType.STRING)
    private OnOffline onOffline;


    public void updateRejectStatus() {  status = MentoringApplyStatus.REJECTED; }
    public void updateAcceptStatus() {   status = MentoringApplyStatus.ACCEPT; }

    public void updateApplyStatus(MentoringApplyStatus status){ this.status = status; }



}
