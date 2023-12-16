package com.goorm.devlink.mentoringservice.config;


import com.goorm.devlink.mentoringservice.util.MessageUtil;
import com.goorm.devlink.mentoringservice.util.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MentoringConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public ModelMapperUtil modelMapperUtil(ModelMapper modelMapper){
        return new ModelMapperUtil(modelMapper);
    }
    @Bean
    public MessageUtil messageUtil(MessageSource messageSource){ return new MessageUtil(messageSource); }
}
