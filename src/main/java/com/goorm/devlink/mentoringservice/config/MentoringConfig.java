package com.goorm.devlink.mentoringservice.config;


import com.goorm.devlink.mentoringservice.util.ModelMapperUtil;
import org.modelmapper.ModelMapper;
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
}
