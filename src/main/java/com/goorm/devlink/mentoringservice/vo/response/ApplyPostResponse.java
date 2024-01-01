package com.goorm.devlink.mentoringservice.vo.response;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.vo.MentoringApplyStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
public class ApplyPostResponse {

    private String applyUuid;
    private MentoringApplyStatus applyStatus;
    private String postUuid;
    private String postImageUrl;
    private String postTitle;
    private List<String> stacks;
    private String address;

    public static ApplyPostResponse getInstance(MentoringApply mentoringApply,
                                                List<MentoringPostResponse> mentoringPostResponses){
        MentoringPostResponse postResponse = findPostResponse(mentoringPostResponses, mentoringApply.getPostUuid());
        return ApplyPostResponse.builder()
                .applyUuid(mentoringApply.getApplyUuid())
                .applyStatus(mentoringApply.getStatus())
                .postUuid(mentoringApply.getPostUuid())
                .postImageUrl(postResponse.getPostImageUrl())
                .postTitle(postResponse.getPostTitle())
                .stacks(postResponse.getStacks())
                .address(postResponse.getAddress())
                .build();
    }

    private static MentoringPostResponse findPostResponse(List<MentoringPostResponse> mentoringPostResponses,String postUuid){
        return mentoringPostResponses.stream()
                .filter(mentoringPostResponse -> mentoringPostResponse.getPostUuid().equals(postUuid)).findFirst().get();

    }

}
