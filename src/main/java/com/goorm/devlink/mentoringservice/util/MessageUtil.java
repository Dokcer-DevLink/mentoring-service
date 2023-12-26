package com.goorm.devlink.mentoringservice.util;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSource;

    public String getApplyCompleteMessage(){ return getMessage("response.apply.complete"); }
    public String getMentoringCreateMessage(){ return getMessage("response.mentoring.create");}
    public String getMentoringRejectMessage() { return getMessage("response.mentoring.reject");}
    public String getMentoringStatusUpdateMessage() {
        return getMessage("response.mentoring.update.status");
    }
    public String getUserUuidEmptyMessage() {
        return getMessage("request.empty.userUuid");
    }
    public String getApplyUuidEmptyMessage() {return getMessage("request.empty.applyUuid");}
    public String getMentoringUuidEmptyMessage() {return getMessage("request.empty.mentoringUuid");}
    public String getApplyUuidNoSuchMessage(String applyUuid) {
        return getMessage("request.nosuch.applyUuid", new String[] {applyUuid});
    }
    public String getMentoringUuidNoSuchMessage(String mentoringUuid) {
        return getMessage("request.nosuch.mentoringUuid", new String[] {mentoringUuid});
    }
    public String getMentoringCreateErrorMessage() {
        return getMessage("runtime.error.mentoring.create");}
    public String getMentoringStatusUpdateErrorMessage() {
        return getMessage("runtime.error.mentoring.update");}
    public String getKafkaNotifyErrorMessage() {
        return getMessage("runtime.error.kafka.notification");}
    public String getProfileConnectionErrorMessage() {
        return getMessage("runtime.error.profile.connection"); }

    private String getMessage(String messageCode){
        return messageSource.getMessage(messageCode,new String[]{}, Locale.KOREA);
    }
    private String getMessage(String messageCode, String[] parameters){
        return messageSource.getMessage(messageCode,parameters, Locale.KOREA);
    }



}
