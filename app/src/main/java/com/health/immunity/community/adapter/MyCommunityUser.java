package com.health.immunity.community.adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyCommunityUser implements Serializable
{

    @SerializedName("user_count")
    @Expose
    private Integer userCount;
    @SerializedName("community_name")
    @Expose
    private String communityName;

    @SerializedName("all_over_sympton")
    @Expose
    private String alloversympton;

    @SerializedName("community_type")
    @Expose
    private String community_type;
    private final static long serialVersionUID = 6274224180302102189L;

    public Integer getUserCount() {
        return userCount;
    }

    public String getAlloversympton() {
        return alloversympton;
    }

    public void setAlloversympton(String alloversympton) {
        this.alloversympton = alloversympton;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunity_type() {
        return community_type;
    }

    public void setCommunity_type(String community_type) {
        this.community_type = community_type;
    }
}
