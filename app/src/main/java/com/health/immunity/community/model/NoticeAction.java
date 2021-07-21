package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeAction {

    @SerializedName("actionName")
    @Expose
    private String actionName;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("session_link")
    @Expose
    private String session_link;

    @SerializedName("order_id")
    @Expose
    private String order_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSession_link() {
        return session_link;
    }

    public void setSession_link(String session_link) {
        this.session_link = session_link;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

