package com.goorm.devlink.mentoringservice.vo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MentoringPostResponse {

    private String postUuid;
    private String postImageUrl;
    private String postTitle;
    private List<String> stacks;
    private String address;

}
