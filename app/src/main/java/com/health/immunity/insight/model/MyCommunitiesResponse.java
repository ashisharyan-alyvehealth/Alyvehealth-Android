package com.health.immunity.insight.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyCommunitiesResponse implements Serializable {

    @SerializedName("jsonData")
    @Expose
    private JsonData jsonData;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = 542380432899970571L;

    public JsonData getJsonData() {
        return jsonData;
    }

    public void setJsonData(JsonData jsonData) {
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


    public class All implements Serializable {
        private boolean isChecked = false;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        @SerializedName("community_id")
        @Expose
        private Integer communityId;
        @SerializedName("community_name")
        @Expose
        private String communityName;
        @SerializedName("community_type")
        @Expose
        private String communityType;
        @SerializedName("member_status")
        @Expose
        private String memberStatus;
        @SerializedName("user_count")
        @Expose
        private Integer userCount;
        @SerializedName("user_type")
        @Expose
        private String user_type;
        @SerializedName("redirect_url")
        @Expose
        private String redirect_url;

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        private final static long serialVersionUID = 2250989803257858095L;

        public Integer getCommunityId() {
            return communityId;
        }

        public void setCommunityId(Integer communityId) {
            this.communityId = communityId;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getCommunityType() {
            return communityType;
        }

        public void setCommunityType(String communityType) {
            this.communityType = communityType;
        }

        public String getMemberStatus() {
            return memberStatus;
        }

        public void setMemberStatus(String memberStatus) {
            this.memberStatus = memberStatus;
        }

        public Integer getUserCount() {
            return userCount;
        }

        public void setUserCount(Integer userCount) {
            this.userCount = userCount;
        }

    }


    public class JsonData implements Serializable {

        @SerializedName("Pending")
        @Expose
        private List<Pending> pending = null;
        /*  @SerializedName("Verified")
          @Expose
          private List<Verified> verified = null;*/
        @SerializedName("All")
        @Expose
        private List<All> all = null;
        private final static long serialVersionUID = 5335704746281675933L;

        public List<Pending> getPending() {
            return pending;
        }

        public void setPending(List<Pending> pending) {
            this.pending = pending;
        }

       /* public List<Verified> getVerified() {
            return verified;
        }

        public void setVerified(List<Verified> verified) {
            this.verified = verified;
        }*/

        public List<All> getAll() {
            return all;
        }

        public void setAll(List<All> all) {
            this.all = all;
        }

    }


    public class Pending implements Serializable {

        @SerializedName("community_id")
        @Expose
        private Integer communityId;
        @SerializedName("community_name")
        @Expose
        private String communityName;
        @SerializedName("community_type")
        @Expose
        private String communityType;
        @SerializedName("member_status")
        @Expose
        private String memberStatus;
        @SerializedName("user_count")
        @Expose
        private Integer userCount;

        @SerializedName("user_type")
        @Expose
        private String user_type;

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        private final static long serialVersionUID = 8838728652598697397L;

        public Integer getCommunityId() {
            return communityId;
        }

        public void setCommunityId(Integer communityId) {
            this.communityId = communityId;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getCommunityType() {
            return communityType;
        }

        public void setCommunityType(String communityType) {
            this.communityType = communityType;
        }

        public String getMemberStatus() {
            return memberStatus;
        }

        public void setMemberStatus(String memberStatus) {
            this.memberStatus = memberStatus;
        }

        public Integer getUserCount() {
            return userCount;
        }

        public void setUserCount(Integer userCount) {
            this.userCount = userCount;
        }

    }


    public class Verified implements Serializable {

        @SerializedName("community_id")
        @Expose
        private Integer communityId;
        @SerializedName("community_name")
        @Expose
        private String communityName;
        @SerializedName("community_type")
        @Expose
        private String communityType;
        @SerializedName("member_status")
        @Expose
        private String memberStatus;
        @SerializedName("user_count")
        @Expose
        private Integer userCount;
        @SerializedName("user_type")
        @Expose
        private String user_type;

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        private final static long serialVersionUID = -1960611654567894007L;

        public Integer getCommunityId() {
            return communityId;
        }

        public void setCommunityId(Integer communityId) {
            this.communityId = communityId;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getCommunityType() {
            return communityType;
        }

        public void setCommunityType(String communityType) {
            this.communityType = communityType;
        }

        public String getMemberStatus() {
            return memberStatus;
        }

        public void setMemberStatus(String memberStatus) {
            this.memberStatus = memberStatus;
        }

        public Integer getUserCount() {
            return userCount;
        }

        public void setUserCount(Integer userCount) {
            this.userCount = userCount;
        }

    }
}
