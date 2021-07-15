package com.health.immunity.insight.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GraphResponse implements Serializable {

    @SerializedName("jsonData")
    @Expose
    private JsonData jsonData;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    //
    @SerializedName("day")
    @Expose
    private String daystatus;
    @SerializedName("week")
    @Expose
    private String weekstatus;
    @SerializedName("month")
    @Expose
    private String monthstatus;


    public String getDaystatus() {
        return daystatus;
    }

    public void setDaystatus(String daystatus) {
        this.daystatus = daystatus;
    }

    public String getWeekstatus() {
        return weekstatus;
    }

    public void setWeekstatus(String weekstatus) {
        this.weekstatus = weekstatus;
    }

    public String getMonthstatus() {
        return monthstatus;
    }

    public void setMonthstatus(String monthstatus) {
        this.monthstatus = monthstatus;
    }

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


    public class AllTrend implements Serializable
    {

        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("value")
        @Expose
        private String value;
        private final static long serialVersionUID = -418943524164010194L;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }




    public class JsonData implements Serializable
    {

        @SerializedName("my_trends")
        @Expose
        private List<MyTrend> myTrends = null;
        @SerializedName("all_trends")
        @Expose
        private List<AllTrend> allTrends = null;
        private final static long serialVersionUID = -3790393316142501762L;

        public List<MyTrend> getMyTrends() {
            return myTrends;
        }

        public void setMyTrends(List<MyTrend> myTrends) {
            this.myTrends = myTrends;
        }

        public List<AllTrend> getAllTrends() {
            return allTrends;
        }

        public void setAllTrends(List<AllTrend> allTrends) {
            this.allTrends = allTrends;
        }

    }


    public class MyTrend implements Serializable
    {

        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("value")
        @Expose
        private String value;
        private final static long serialVersionUID = 1759978684500884973L;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
