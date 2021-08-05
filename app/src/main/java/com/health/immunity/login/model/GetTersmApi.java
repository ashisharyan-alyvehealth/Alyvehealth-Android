package com.health.immunity.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTersmApi {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("jsonData")
    @Expose
    private GetTermsData jsonData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetTermsData getJsonData() {
        return jsonData;
    }

    public void setJsonData(GetTermsData jsonData) {
        this.jsonData = jsonData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

