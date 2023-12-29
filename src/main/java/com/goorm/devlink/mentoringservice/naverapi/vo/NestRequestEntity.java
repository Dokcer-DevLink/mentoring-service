package com.goorm.devlink.mentoringservice.naverapi.vo;

import java.util.List;
import java.util.Map;

public class NestRequestEntity {
    private String language = "ko-KR";
    //completion optional, sync/async
    private String completion = "sync";
    //optional, used to receive the analyzed results
    private String callback;
    //optional, any data
    private Map<String, Object> userdata;
    private Boolean wordAlignment = Boolean.FALSE;
    private Boolean fullText = Boolean.TRUE;
    //boosting object array
    private List<Boosting> boostings;
    //comma separated words
    private String forbiddens;
    private Diarization diarization;

    private Sed sed;

    public Sed getSed() {
        return sed;
    }

    public void setSed(Sed sed) { this.sed = sed; }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public String getCallback() {
        return callback;
    }

    public Boolean getWordAlignment() {
        return wordAlignment;
    }

    public void setWordAlignment(Boolean wordAlignment) {
        this.wordAlignment = wordAlignment;
    }

    public Boolean getFullText() {
        return fullText;
    }

    public void setFullText(Boolean fullText) {
        this.fullText = fullText;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public Map<String, Object> getUserdata() {
        return userdata;
    }

    public void setUserdata(Map<String, Object> userdata) {
        this.userdata = userdata;
    }

    public String getForbiddens() {
        return forbiddens;
    }

    public void setForbiddens(String forbiddens) {
        this.forbiddens = forbiddens;
    }

    public List<Boosting> getBoostings() {
        return boostings;
    }

    public void setBoostings(List<Boosting> boostings) {
        this.boostings = boostings;
    }

    public Diarization getDiarization() {
        return diarization;
    }

    public void setDiarization(Diarization diarization) {
        this.diarization = diarization;
    }

}
