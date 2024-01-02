package com.goorm.devlink.mentoringservice.vo.response;

import com.goorm.devlink.mentoringservice.entity.Mentoring;
import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import com.goorm.devlink.mentoringservice.vo.MentoringType;
import com.goorm.devlink.mentoringservice.vo.OnOffline;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter
@Builder
public class MentoringBasicResponse {
    private String mentoringUuid;
    private String targetNickname;
    private String targetImageUrl;
    private String mentorUuid;
    private String menteeUuid;
    private String mentoringPlace;
    private String postUuid;
    private OnOffline onOffline;
    private MentoringStatus status;
    private LocalDateTime startTime;
    private int unitTimeCount;

    public static MentoringBasicResponse getInstance(Mentoring mentoring, List<ProfileSimpleCard> profileList, MentoringType type){

        ProfileSimpleCard profile = (type.equals(MentoringType.MENTOR)) ?
                  getProfileMentor(mentoring, profileList) : getProfileMentee(mentoring,profileList);

        return MentoringBasicResponse.builder()
                .mentoringUuid(mentoring.getMentoringUuid())
                .targetNickname(profile.getNickname())
                .targetImageUrl(profile.getProfileImageUrl())
                .mentorUuid(mentoring.getMentorUuid())
                .menteeUuid(mentoring.getMenteeUuid())
                .mentoringPlace(mentoring.getMentoringPlace())
                .postUuid(mentoring.getPostUuid())
                .onOffline(mentoring.getOnOffline())
                .status(mentoring.getStatus())
                .startTime(mentoring.getStartTime())
                .unitTimeCount(mentoring.getUnitTimeCount())
                .build();
    }

    private static ProfileSimpleCard getProfileMentor(Mentoring mentoring, List<ProfileSimpleCard> profileList){
        return profileList.stream()
                .filter(profile->profile.userUuid.equals(mentoring.getMentorUuid())).findFirst()
                .orElse(ProfileSimpleCard.getInstance());
    }

    private static ProfileSimpleCard getProfileMentee(Mentoring mentoring, List<ProfileSimpleCard> profileList){
        return profileList.stream()
                .filter(profile->profile.userUuid.equals(mentoring.getMenteeUuid())).findFirst()
                .orElse(ProfileSimpleCard.getInstance());
    }


}
