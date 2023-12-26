package com.goorm.devlink.mentoringservice.vo.request;

import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class MentoringStatusRequest {
    @NotBlank(message = "{request.required}")
    private String mentoringUuid;
    @NotNull(message = "{request.required}")
    private MentoringStatus status;

}
