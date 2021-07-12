package com.health.immunity.login.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileOTPModel {

    @SerializedName("jsonData")
    @Expose
    private MobileOTPData jsonData;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("onboarding")
    @Expose
    private String onboarding;
    @SerializedName("today_status")
    @Expose
    private String today_status;


    public MobileOTPData getJsonData() {
        return jsonData;
    }

    public void setJsonData(MobileOTPData jsonData) {
        this.jsonData = jsonData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOnboarding() {
        return onboarding;
    }

    public void setOnboarding(String onboarding) {
        this.onboarding = onboarding;
    }

    public String getToday_status() {
        return today_status;
    }

    public void setToday_status(String today_status) {
        this.today_status = today_status;
    }
}

