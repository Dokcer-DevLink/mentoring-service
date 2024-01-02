package com.goorm.devlink.mentoringservice.vo.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProfileSimpleCard {

    String userUuid;
    String profileImageUrl;
    String nickname;
    String address;
    List<String> stacks;

    public static ProfileSimpleCard getInstance(){
        return new ProfileSimpleCard();
    }


}
