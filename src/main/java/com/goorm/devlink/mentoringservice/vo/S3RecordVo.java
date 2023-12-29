package com.goorm.devlink.mentoringservice.vo;

import com.amazonaws.util.Base64;
import lombok.Builder;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Getter
@Builder
public class S3RecordVo {

    private static final String FOLDER_NAME = "record/";
    private String contentType;
    private byte[] content;
    private long size;
    private String fileName;
    private InputStream inputStream;
    public static S3RecordVo getInstance(String encodingData,String mentoringUuid){
        String[] arr = encodingData.split(",");
        byte[] content = Base64.decode(arr[1]);
        String contentType = arr[0].substring(arr[0].indexOf(":") + 1, arr[0].indexOf(";"));

        return S3RecordVo.builder()
                .contentType(contentType)
                .content(content)
                .size(content.length)
                .fileName(FOLDER_NAME.concat(mentoringUuid))
                .inputStream(new ByteArrayInputStream(content))
                .build();
    }

    public static boolean isNotValidContents(String encodingData){
        return ( encodingData.split(",").length == 2)? false : true;
    }
    public static boolean isNotValidType(String encodingData){
        String[] arr = encodingData.split(",");
        String contentType = arr[0].substring(arr[0].indexOf(":") + 1, arr[0].indexOf(";"));
        return (    S3RecordType.MPEG.getContentType().equals(contentType)) ? false : true;
    }

    @Getter
    public enum S3RecordType{
        MPEG("audio/mpeg");

        private String contentType;
        S3RecordType(String contentType){
            this.contentType = contentType;
        }
    }

}
