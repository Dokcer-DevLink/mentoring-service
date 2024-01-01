package com.goorm.devlink.mentoringservice.vo.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class MentoringPostResponse {

    private String postUuid;
    private String postImageUrl;
    private String postTitle;
    private List<String> stacks;
    private String address;

    public static MentoringPostResponse getInstance() {
        return new MentoringPostResponse();
    }
}
