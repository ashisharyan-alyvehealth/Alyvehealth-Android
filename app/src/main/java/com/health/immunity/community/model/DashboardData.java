package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.health.immunity.community.adapter.MyCommunityUser;
import com.health.immunity.profile.model.HomeLocation;
import com.health.immunity.profile.model.OfficeLocation;

import java.util.List;

public class DashboardData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("hr_id")
    @Expose
    private String hrId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("office_location")
    @Expose
    private OfficeLocation officeLocation;
    @SerializedName("home_location")
    @Expose
    private HomeLocation homeLocation;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("mobile_password")
    @Expose
    private String mobilePassword;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("check_email")
    @Expose
    private String checkEmail;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("remember_token")
    @Expose
    private String rememberToken;
    @SerializedName("access_code")
    @Expose
    private String accessCode;
    @SerializedName("organisation_name")
    @Expose
    private String organisationName;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("linked_id_url")
    @Expose
    private String linkedIdUrl;
    @SerializedName("business_card")
    @Expose
    private String businessCard;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("create_mode")
    @Expose
    private String createMode;
    @SerializedName("change_mode")
    @Expose
    private String changeMode;
    @SerializedName("today_status")
    @Expose
    private String today_status;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("access_control")
    @Expose
    private String access_control;
    @SerializedName("auto_check_in")
    @Expose
    private String auto_check_in;

    @SerializedName("manage")
    @Expose
    private String manage;

    @SerializedName("symptom_display")
    @Expose
    private String symptom_display;

    public String getSymptom_display() {
        return symptom_display;
    }

    public void setSymptom_display(String symptom_display) {
        this.symptom_display = symptom_display;
    }

    @SerializedName("redirect_url")
    @Expose
    private String redirect_url;

    @SerializedName("last_five_location")
    @Expose
    private List<LastFiveLocation> lastFiveLocation = null;

    @SerializedName("insights")
    @Expose
    private InsightData insights;

    @SerializedName("my_community_users")
    @Expose
    private List<MyCommunityUser> myCommunityUsers = null;


    @SerializedName("all_over_sympton_calculated")
    @Expose
    private String alloversymptoncalculated;


    @SerializedName("verified_community_name")
    @Expose
    private String verified_community_name;

    @SerializedName("google_healthkit_status")
    @Expose
    private String google_healthkit_status;

    public String getGoogle_healthkit_status() {
        return google_healthkit_status;
    }

    public void setGoogle_healthkit_status(String google_healthkit_status) {
        this.google_healthkit_status = google_healthkit_status;
    }

    @SerializedName("corporate_subscriber")
    @Expose
    private Integer corporatesubscriber;
    @SerializedName("yesterday_proximity")
    @Expose
    private Integer yesterdayproximity;
    @SerializedName("last_week_proximity")
    @Expose
    private Integer lastweekproximity;

    @SerializedName("api_keys")
    @Expose
    private List<ApiKey> apiKeys = null;

    @SerializedName("contents")
    @Expose
    private Contents contents;

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public List<ApiKey> getApiKeys() {
        return apiKeys;
    }

    public void setApiKeys(List<ApiKey> apiKeys) {
        this.apiKeys = apiKeys;
    }

    public String getAlloversymptoncalculated() {
        return alloversymptoncalculated;
    }

    public void setAlloversymptoncalculated(String alloversymptoncalculated) {
        this.alloversymptoncalculated = alloversymptoncalculated;
    }

    public Integer getCorporatesubscriber() {
        return corporatesubscriber;
    }

    public void setCorporatesubscriber(Integer corporatesubscriber) {
        this.corporatesubscriber = corporatesubscriber;
    }

    public Integer getYesterdayproximity() {
        return yesterdayproximity;
    }

    public void setYesterdayproximity(Integer yesterdayproximity) {
        this.yesterdayproximity = yesterdayproximity;
    }

    public Integer getLastweekproximity() {
        return lastweekproximity;
    }

    public void setLastweekproximity(Integer lastweekproximity) {
        this.lastweekproximity = lastweekproximity;
    }

    public String getManage() {
        return manage;
    }

    public void setManage(String manage) {
        this.manage = manage;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getAuto_check_in() {
        return auto_check_in;
    }

    public void setAuto_check_in(String auto_check_in) {
        this.auto_check_in = auto_check_in;
    }

    @SerializedName("user_today")
    @Expose
    private UserToday userToday = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHrId() {
        return hrId;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public OfficeLocation getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(OfficeLocation officeLocation) {
        this.officeLocation = officeLocation;
    }

    public HomeLocation getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(HomeLocation homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobilePassword() {
        return mobilePassword;
    }

    public void setMobilePassword(String mobilePassword) {
        this.mobilePassword = mobilePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCheckEmail() {
        return checkEmail;
    }

    public void setCheckEmail(String checkEmail) {
        this.checkEmail = checkEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getLinkedIdUrl() {
        return linkedIdUrl;
    }

    public void setLinkedIdUrl(String linkedIdUrl) {
        this.linkedIdUrl = linkedIdUrl;
    }

    public String getBusinessCard() {
        return businessCard;
    }

    public void setBusinessCard(String businessCard) {
        this.businessCard = businessCard;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCreateMode() {
        return createMode;
    }

    public void setCreateMode(String createMode) {
        this.createMode = createMode;
    }

    public String getChangeMode() {
        return changeMode;
    }

    public void setChangeMode(String changeMode) {
        this.changeMode = changeMode;
    }

    public UserToday getUserToday() {
        return userToday;
    }

    public void setUserToday(UserToday userToday) {
        this.userToday = userToday;
    }

    public String getToday_status() {
        return today_status;
    }

    public void setToday_status(String today_status) {
        this.today_status = today_status;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAccess_control() {
        return access_control;
    }

    public void setAccess_control(String access_control) {
        this.access_control = access_control;
    }

    public List<LastFiveLocation> getLastFiveLocation() {
        return lastFiveLocation;
    }

    public void setLastFiveLocation(List<LastFiveLocation> lastFiveLocation) {
        this.lastFiveLocation = lastFiveLocation;
    }

    public InsightData getInsights() {
        return insights;
    }

    public void setInsights(InsightData insights) {
        this.insights = insights;
    }

    public List<MyCommunityUser> getMyCommunityUsers() {
        return myCommunityUsers;
    }

    public void setMyCommunityUsers(List<MyCommunityUser> myCommunityUsers) {
        this.myCommunityUsers = myCommunityUsers;
    }

    public String getVerified_community_name() {
        return verified_community_name;
    }

    public void setVerified_community_name(String verified_community_name) {
        this.verified_community_name = verified_community_name;
    }

    public class ApiKey {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("api_name")
        @Expose
        private String apiName;
        @SerializedName("api_key")
        @Expose
        private String apiKey;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
    public class Contents {


        @SerializedName("watch")
        @Expose
        private List<Watch> watch = null;




        public List<Watch> getWatch() {
            return watch;
        }

        public void setWatch(List<Watch> watch) {
            this.watch = watch;
        }



    }
    public class Watch {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("content_type")
        @Expose
        private String contentType;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("short_description")
        @Expose
        private String shortDescription;
        @SerializedName("long_description")
        @Expose
        private String longDescription;
        @SerializedName("photo_link")
        @Expose
        private Object photoLink;
        @SerializedName("content_link")
        @Expose
        private String contentLink;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("valid_from")
        @Expose
        private String validFrom;
        @SerializedName("valid_to")
        @Expose
        private String validTo;
        @SerializedName("labels")
        @Expose
        private String labels;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("readtime")
        @Expose
        private String readtime;
        @SerializedName("watchtime")
        @Expose
        private String watchtime;
        @SerializedName("character_count")
        @Expose
        private String characterCount;
        @SerializedName("priority")
        @Expose
        private String priority;
        @SerializedName("context")
        @Expose
        private String context;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("updated_by")
        @Expose
        private Integer updatedBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getLongDescription() {
            return longDescription;
        }

        public void setLongDescription(String longDescription) {
            this.longDescription = longDescription;
        }

        public Object getPhotoLink() {
            return photoLink;
        }

        public void setPhotoLink(Object photoLink) {
            this.photoLink = photoLink;
        }

        public String getContentLink() {
            return contentLink;
        }

        public void setContentLink(String contentLink) {
            this.contentLink = contentLink;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getValidFrom() {
            return validFrom;
        }

        public void setValidFrom(String validFrom) {
            this.validFrom = validFrom;
        }

        public String getValidTo() {
            return validTo;
        }

        public void setValidTo(String validTo) {
            this.validTo = validTo;
        }

        public String getLabels() {
            return labels;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getReadtime() {
            return readtime;
        }

        public void setReadtime(String readtime) {
            this.readtime = readtime;
        }

        public String getWatchtime() {
            return watchtime;
        }

        public void setWatchtime(String watchtime) {
            this.watchtime = watchtime;
        }

        public String getCharacterCount() {
            return characterCount;
        }

        public void setCharacterCount(String characterCount) {
            this.characterCount = characterCount;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Integer getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Integer updatedBy) {
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

    }
}
