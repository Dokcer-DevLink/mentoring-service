package com.goorm.devlink.mentoringservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.goorm.devlink.mentoringservice.config.properties.vo.AwsConfigVo;
import com.goorm.devlink.mentoringservice.util.AwsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    @Bean
    public AwsConfigVo awsConfigVo(){
        return new AwsConfigVo();
    }
    @Bean
    public AmazonS3Client amazonS3Client(AwsConfigVo awsConfigVo){
        BasicAWSCredentials credentials =
                new BasicAWSCredentials(awsConfigVo.getAccessKey(),awsConfigVo.getSecretKey());

        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(awsConfigVo.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @Bean
    public AwsUtil awsUtil(AwsConfigVo awsConfigVo, AmazonS3Client amazonS3Client ){
        return new AwsUtil(amazonS3Client,awsConfigVo);
    }

}
