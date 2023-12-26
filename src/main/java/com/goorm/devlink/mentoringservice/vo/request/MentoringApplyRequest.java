package com.goorm.devlink.mentoringservice.vo.request;


import com.goorm.devlink.mentoringservice.vo.MentoringType;
import com.goorm.devlink.mentoringservice.vo.OnOffline;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class MentoringApplyRequest {

    @NotBlank(message = "{request.required}")
    private String postUuid;
    @NotBlank(message = "{request.required}")
    private String targetUuid;
    @NotNull(message = "{request.required}")
    private MentoringType targetType;
    @NotBlank(message = "{request.required}")
    private String mentoringPlace;
    @NotNull(message = "{request.required}")
    private OnOffline onOffline;
    @NotNull(message = "{request.required}")
    private LocalDateTime startTime;
    @NotNull(message = "{request.required}")
    private int unitTimeCount;


}
