package com.health.immunity.act.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SourceResponse {

    @SerializedName("jsonData")
    @Expose
    private List<JsonDatum> jsonData = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<JsonDatum> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<JsonDatum> jsonData) {
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


    public class JsonDatum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("wallet")
        @Expose
        private String wallet;
        @SerializedName("token_name")
        @Expose
        private String tokenName;
        @SerializedName("token_value")
        @Expose
        private String tokenValue;
        @SerializedName("status")
        @Expose
        private String status;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTokenName() {
            return tokenName;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }

        public String getTokenValue() {
            return tokenValue;
        }

        public void setTokenValue(String tokenValue) {
            this.tokenValue = tokenValue;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }
    }
}
