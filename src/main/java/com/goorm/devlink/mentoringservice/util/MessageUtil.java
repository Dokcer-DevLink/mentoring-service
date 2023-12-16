package com.goorm.devlink.mentoringservice.util;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSource;

    public String getUserUuidEmptyMessage() {
        return getMessage("request.empty.userUuid");
    }

    public String getApplyUuidEmptyMessage() {
        return getMessage("request.empty.applyUuid");
    }

    public String getMentoringUuidEmptyMessage() {
        return getMessage("request.empty.mentoringUuid");
    }

    public String getUserUuidNoSuchMessage(String userUuid) {

        return getMessage("request.nosuch.userUuid", new String[] {userUuid});
    }

    public String getApplyUuidNoSuchMessage(String applyUuid) {
        return getMessage("request.nosuch.applyUuid", new String[] {applyUuid});
    }

    public String getMentoringUuidNoSuchMessage(String mentoringUuid) {
        return getMessage("request.nosuch.mentoringUuid", new String[] {mentoringUuid});
    }

    private String getMessage(String messageCode){
        return messageSource.getMessage(messageCode,new String[]{}, Locale.KOREA);
    }

    private String getMessage(String messageCode, String[] parameters){
        return messageSource.getMessage(messageCode,parameters, Locale.KOREA);
    }



}
