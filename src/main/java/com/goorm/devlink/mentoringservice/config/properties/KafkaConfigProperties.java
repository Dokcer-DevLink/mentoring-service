package com.goorm.devlink.mentoringservice.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("data.kafka")
@Getter
@Setter
public class KafkaConfigProperties {

    private String topicName;
    private String groupId;
    private String bootstrapServerUrl;


}
