package com.goorm.devlink.mentoringservice.config.properties.vo;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Getter @Setter
@Slf4j
public class KafkaConfigVo {

    private final String topicName;
    private final String groupId;
    private final String bootstrapServerUrl;

    @PostConstruct
    public void init(){
        log.info("topicName : {}", topicName);
        log.info("groupId : {} ", groupId);
        log.info("bootStrapServerUrl {}", bootstrapServerUrl);
    }
}
