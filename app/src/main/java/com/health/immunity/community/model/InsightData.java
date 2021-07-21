package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsightData {

    @SerializedName("symptomatic")
    @Expose
    private Integer symptomatic;

    @SerializedName("symptomatic_in_week")
    @Expose
    private Integer symptomatic_in_week;

    @SerializedName("homelocation")
    @Expose
    private Integer homelocation;
    @SerializedName("officelocation")
    @Expose
    private Integer officelocation;

    public Integer getSymptomatic() {
        return symptomatic;
    }

    public Integer getSymptomatic_in_week() {
        return symptomatic_in_week;
    }

    public void setSymptomatic_in_week(Integer symptomatic_in_week) {
        this.symptomatic_in_week = symptomatic_in_week;
    }

    public void setSymptomatic(Integer symptomatic) {
        this.symptomatic = symptomatic;
    }

    public Integer getHomelocation() {
        return homelocation;
    }

    public void setHomelocation(Integer homelocation) {
        this.homelocation = homelocation;
    }

    public Integer getOfficelocation() {
        return officelocation;
    }

    public void setOfficelocation(Integer officelocation) {
        this.officelocation = officelocation;
    }
}

