package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CommunityUserListResponse implements Serializable {

    @SerializedName("jsonData")
    @Expose
    private List<JsonDatum> jsonData = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = -4445318265416342541L;

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


    public class JsonDatum implements Serializable {

        @SerializedName("community_name")
        @Expose
        private String communityName;
        @SerializedName("community_id")
        @Expose
        private Integer communityId;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("member_status")
        @Expose
        private String memberStatus;
        private final static long serialVersionUID = -3193006052837712695L;

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public Integer getCommunityId() {
            return communityId;
        }

        public void setCommunityId(Integer communityId) {
            this.communityId = communityId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUserName() {
            return userName;
        }

        public Integer getUserId() {
            return userId;
        }
        public String getUserType() {
            return userType;
        }


        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMemberStatus() {
            return memberStatus;
        }

        public void setMemberStatus(String memberStatus) {
            this.memberStatus = memberStatus;
        }

    }
}
