package com.goorm.devlink.mentoringservice.vo.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RecordRequest {

    @NotBlank(message = "{request.required}")
    private String mentoringUuid;
    @NotBlank(message = "{request.required}")
    private String content;
}
