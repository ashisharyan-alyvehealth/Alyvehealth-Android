package com.health.immunity.insight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetKpiCall implements Serializable {

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

    @SerializedName("body")
    @Expose
    private String bodystatus;
    @SerializedName("activity")
    @Expose
    private String activitystatus;

    public String getBodystatus() {
        return bodystatus;
    }

    public void setBodystatus(String bodystatus) {
        this.bodystatus = bodystatus;
    }

    public String getActivitystatus() {
        return activitystatus;
    }

    public void setActivitystatus(String activitystatus) {
        this.activitystatus = activitystatus;
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


    public class Activity implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("alias")
        @Expose
        private String alias;
        @SerializedName("healthy_range_source")
        @Expose
        private String healthy_range_source;

        public String getHealthy_range_source() {
            return healthy_range_source;
        }

        public void setHealthy_range_source(String healthy_range_source) {
            this.healthy_range_source = healthy_range_source;
        }

        @SerializedName("tab")
        @Expose
        private String tab;
        @SerializedName("lower_range")
        @Expose
        private String lowerRange;
        @SerializedName("upper_range")
        @Expose
        private String upperRange;
        @SerializedName("healthy_lower_range")
        @Expose
        private String healthyLowerRange;
        @SerializedName("healthy_upper_range")
        @Expose
        private String healthyUpperRange;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("compare_value")
        @Expose
        private String comparevalue;

        public String getComparevalue() {
            return comparevalue;
        }

        public void setComparevalue(String comparevalue) {
            this.comparevalue = comparevalue;
        }

        private final static long serialVersionUID = -3778238093951881027L;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }

        public String getLowerRange() {
            return lowerRange;
        }

        public void setLowerRange(String lowerRange) {
            this.lowerRange = lowerRange;
        }

        public String getUpperRange() {
            return upperRange;
        }

        public void setUpperRange(String upperRange) {
            this.upperRange = upperRange;
        }

        public String getHealthyLowerRange() {
            return healthyLowerRange;
        }

        public void setHealthyLowerRange(String healthyLowerRange) {
            this.healthyLowerRange = healthyLowerRange;
        }

        public String getHealthyUpperRange() {
            return healthyUpperRange;
        }

        public void setHealthyUpperRange(String healthyUpperRange) {
            this.healthyUpperRange = healthyUpperRange;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }


    public class Body implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("alias")
        @Expose
        private String alias;

        public String getHealthy_range_source() {
            return healthy_range_source;
        }

        public void setHealthy_range_source(String healthy_range_source) {
            this.healthy_range_source = healthy_range_source;
        }

        @SerializedName("healthy_range_source")
        @Expose
        private String healthy_range_source;

        @SerializedName("tab")
        @Expose
        private String tab;
        @SerializedName("lower_range")
        @Expose
        private String lowerRange;
        @SerializedName("upper_range")
        @Expose
        private String upperRange;
        @SerializedName("healthy_lower_range")
        @Expose
        private String healthyLowerRange;
        @SerializedName("healthy_upper_range")
        @Expose
        private String healthyUpperRange;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("value")
        @Expose
        private String value;

        @SerializedName("compare_value")
        @Expose
        private String comparevalue;

        public String getComparevalue() {
            return comparevalue;
        }

        public void setComparevalue(String comparevalue) {
            this.comparevalue = comparevalue;
        }
        private final static long serialVersionUID = -6878405185708621371L;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }

        public String getLowerRange() {
            return lowerRange;
        }

        public void setLowerRange(String lowerRange) {
            this.lowerRange = lowerRange;
        }

        public String getUpperRange() {
            return upperRange;
        }

        public void setUpperRange(String upperRange) {
            this.upperRange = upperRange;
        }

        public String getHealthyLowerRange() {
            return healthyLowerRange;
        }

        public void setHealthyLowerRange(String healthyLowerRange) {
            this.healthyLowerRange = healthyLowerRange;
        }

        public String getHealthyUpperRange() {
            return healthyUpperRange;
        }

        public void setHealthyUpperRange(String healthyUpperRange) {
            this.healthyUpperRange = healthyUpperRange;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }


    public class Environment implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("alias")
        @Expose
        private String alias;
        @SerializedName("tab")
        @Expose
        private String tab;
        @SerializedName("lower_range")
        @Expose
        private String lowerRange;
        @SerializedName("upper_range")
        @Expose
        private String upperRange;
        @SerializedName("healthy_lower_range")
        @Expose
        private String healthyLowerRange;
        @SerializedName("healthy_upper_range")
        @Expose
        private String healthyUpperRange;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("value")
        @Expose
        private String value;

        @SerializedName("compare_value")
        @Expose
        private String comparevalue;

        public String getComparevalue() {
            return comparevalue;
        }

        public void setComparevalue(String comparevalue) {
            this.comparevalue = comparevalue;
        }
        private final static long serialVersionUID = -9098598121877698688L;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }

        public String getLowerRange() {
            return lowerRange;
        }

        public void setLowerRange(String lowerRange) {
            this.lowerRange = lowerRange;
        }

        public String getUpperRange() {
            return upperRange;
        }

        public void setUpperRange(String upperRange) {
            this.upperRange = upperRange;
        }

        public String getHealthyLowerRange() {
            return healthyLowerRange;
        }

        public void setHealthyLowerRange(String healthyLowerRange) {
            this.healthyLowerRange = healthyLowerRange;
        }

        public String getHealthyUpperRange() {
            return healthyUpperRange;
        }

        public void setHealthyUpperRange(String healthyUpperRange) {
            this.healthyUpperRange = healthyUpperRange;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }


    public class JsonData implements Serializable {

        @SerializedName("Body")
        @Expose
        private List<Body> body = null;
        @SerializedName("Activity")
        @Expose
        private List<Activity> activity = null;
        @SerializedName("Environment")
        @Expose
        private List<Environment> environment = null;
        private final static long serialVersionUID = -5119249640406814450L;

        public List<Body> getBody() {
            return body;
        }

        public void setBody(List<Body> body) {
            this.body = body;
        }

        public List<Activity> getActivity() {
            return activity;
        }

        public void setActivity(List<Activity> activity) {
            this.activity = activity;
        }

        public List<Environment> getEnvironment() {
            return environment;
        }

        public void setEnvironment(List<Environment> environment) {
            this.environment = environment;
        }

    }

}
