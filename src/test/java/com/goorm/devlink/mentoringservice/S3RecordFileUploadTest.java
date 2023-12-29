package com.goorm.devlink.mentoringservice;


import com.amazonaws.util.IOUtils;
import com.goorm.devlink.mentoringservice.util.AwsUtil;
import com.goorm.devlink.mentoringservice.vo.S3RecordVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
public class S3RecordFileUploadTest {


    @Autowired
    private AwsUtil awsUtil;

    @Test
    public void mp3UploadTest() throws IOException {

        FileInputStream fis = new FileInputStream("/Users/kangmingu/Desktop/base64.online.txt");
        String encodingFile = IOUtils.toString(fis);

        S3RecordVo s3RecordVo = S3RecordVo.getInstance(encodingFile,"11111111");
        String url = awsUtil.saveRecordInS3Bucket(s3RecordVo);
        System.out.println(url);
    }

}
