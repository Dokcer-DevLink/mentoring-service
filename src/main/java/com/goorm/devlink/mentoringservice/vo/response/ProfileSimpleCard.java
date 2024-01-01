package com.goorm.devlink.mentoringservice.vo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileSimpleCard {

    String userUuid;
    String profileImageUrl;
    String nickname;
    String address;
    List<String> stacks;
}
