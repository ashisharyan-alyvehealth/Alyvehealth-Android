package com.health.immunity.login.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileOTPData {

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
    /*@SerializedName("office_location")
    @Expose
    private String officeLocation;
    @SerializedName("home_location")
    @Expose
    private String homeLocation;*/
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
    private Integer accessCode;
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
    private Integer empId;
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
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("today_status")
    @Expose
    private String today_status;
    @SerializedName("onboarding")
    @Expose
    private String onboarding;
    @SerializedName("onboarding_status")
    @Expose
    private String onboarding_status;

    @SerializedName("is_verified_community")
    @Expose
    private Integer is_verified_community;

    public Integer getIs_verified_community() {
        return is_verified_community;
    }

    public void setIs_verified_community(Integer is_verified_community) {
        this.is_verified_community = is_verified_community;
    }

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

    /*public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }*/

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

    public Integer getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(Integer accessCode) {
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

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToday_status() {
        return today_status;
    }

    public void setToday_status(String today_status) {
        this.today_status = today_status;
    }

    public String getOnboarding() {
        return onboarding;
    }

    public void setOnboarding(String onboarding) {
        this.onboarding = onboarding;
    }

    public String getOnboarding_status() {
        return onboarding_status;
    }

    public void setOnboarding_status(String onboarding_status) {
        this.onboarding_status = onboarding_status;
    }
}

