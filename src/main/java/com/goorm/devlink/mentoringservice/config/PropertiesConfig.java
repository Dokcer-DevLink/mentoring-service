package com.goorm.devlink.mentoringservice.config;


import com.goorm.devlink.mentoringservice.config.properties.KafkaConfigProperties;
import com.goorm.devlink.mentoringservice.config.properties.vo.KafkaConfigVo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KafkaConfigProperties.class)
@RequiredArgsConstructor
public class PropertiesConfig {

    private final KafkaConfigProperties kafkaConfigProperties;

    @Bean
    public KafkaConfigVo kafkaConfigVo(){
        return new KafkaConfigVo(kafkaConfigProperties.getTopicName(),
                kafkaConfigProperties.getGroupId(),
                kafkaConfigProperties.getBootstrapServerUrl());
    }
}
