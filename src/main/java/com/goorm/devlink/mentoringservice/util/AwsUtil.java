package com.goorm.devlink.mentoringservice.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.goorm.devlink.mentoringservice.config.properties.vo.AwsConfigVo;
import com.goorm.devlink.mentoringservice.vo.S3RecordVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AwsUtil {
    private final AmazonS3Client amazonS3Client;
    private final AwsConfigVo awsConfigVo;


    public String saveRecordInS3Bucket(S3RecordVo s3RecordVo) {
        pushImageToS3Bucket(s3RecordVo,generateAwsMetaData(s3RecordVo));
        return getImageUrlFromBucket(s3RecordVo);
    }

    private ObjectMetadata generateAwsMetaData(S3RecordVo s3RecordVo){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(s3RecordVo.getSize());
        metadata.setContentType(s3RecordVo.getContentType());
        return metadata;
    }

    private void pushImageToS3Bucket(S3RecordVo s3RecordVo, ObjectMetadata metadata){
        if(isFileExistInBucket(s3RecordVo)) deleteFileInBucket(s3RecordVo);
        putFileInBucket(s3RecordVo,metadata);
    }

    private String getImageUrlFromBucket(S3RecordVo s3RecordVo){
        return amazonS3Client.getUrl(awsConfigVo.getBucket(), s3RecordVo.getFileName()).toString();
    }

    private boolean isFileExistInBucket(S3RecordVo s3RecordVo){
        return (amazonS3Client.doesObjectExist(awsConfigVo.getBucket(),s3RecordVo.getFileName())) ? true : false;
    }

    private void deleteFileInBucket(S3RecordVo s3RecordVo){
        amazonS3Client.deleteObject(awsConfigVo.getBucket(),s3RecordVo.getFileName());
    }

    private void putFileInBucket(S3RecordVo s3RecordVo,ObjectMetadata metadata){
        amazonS3Client.putObject(
                awsConfigVo.getBucket(),
                s3RecordVo.getFileName(),
                s3RecordVo.getInputStream(),
                metadata);
    }


}