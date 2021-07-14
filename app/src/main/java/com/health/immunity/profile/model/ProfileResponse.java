package com.health.immunity.profile.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("jsonData")
    @Expose
    private ProfileData jsonData;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public ProfileData getJsonData() {
        return jsonData;
    }

    public void setJsonData(ProfileData jsonData) {
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
}

