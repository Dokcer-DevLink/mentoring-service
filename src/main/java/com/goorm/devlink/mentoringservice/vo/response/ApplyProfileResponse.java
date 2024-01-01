package com.goorm.devlink.mentoringservice.vo.response;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.vo.MentoringApplyStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ApplyProfileResponse {

    private String applyUuid;
    private MentoringApplyStatus applyStatus;
    private String fromUuid;
    private String profileImageUrl;
    private String nickname;
    private String address;
    private List<String> stacks;

    public static ApplyProfileResponse getInstance(MentoringApply mentoringApply,
                                                List<ProfileSimpleCard> profileResponse){

        ProfileSimpleCard profileCard = findPostResponse(profileResponse, mentoringApply.getFromUuid());

        return ApplyProfileResponse.builder()
                .applyUuid(mentoringApply.getApplyUuid())
                .applyStatus(mentoringApply.getStatus())
                .fromUuid(mentoringApply.getFromUuid())
                .profileImageUrl(profileCard.getProfileImageUrl())
                .nickname(profileCard.getNickname())
                .address(profileCard.getAddress())
                .stacks(profileCard.getStacks())
                .build();


    }

    public static ProfileSimpleCard findPostResponse(List<ProfileSimpleCard> profileResponses, String userUuid){
        return profileResponses.stream()
                .filter( profileResponse -> profileResponse.getUserUuid().equals(userUuid)).findFirst().get();
    }

}
