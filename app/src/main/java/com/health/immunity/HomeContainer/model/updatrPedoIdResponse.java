package com.health.immunity.HomeContainer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class updatrPedoIdResponse {

    @SerializedName("jsonData")
    @Expose
    private JsonData jsonData;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    public JsonData getJsonData() {
        return jsonData;
    }

    public void setJsonData(JsonData jsonData) {
        this.jsonData = jsonData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class HomeLocation {

        @SerializedName("home_lat")
        @Expose
        private String homeLat;
        @SerializedName("home_lon")
        @Expose
        private String homeLon;

        public String getHomeLat() {
            return homeLat;
        }

        public void setHomeLat(String homeLat) {
            this.homeLat = homeLat;
        }

        public String getHomeLon() {
            return homeLon;
        }

        public void setHomeLon(String homeLon) {
            this.homeLon = homeLon;
        }

    }


    public class JsonData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("hr_id")
        @Expose
        private Integer hrId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("date_of_birth")
        @Expose
        private String dateOfBirth;
        @SerializedName("months")
        @Expose
        private String months;
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
        @SerializedName("phone_number_encrypt")
        @Expose
        private String phoneNumberEncrypt;
        @SerializedName("mobile_password")
        @Expose
        private String mobilePassword;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("email_encrypt")
        @Expose
        private String emailEncrypt;
        @SerializedName("check_email")
        @Expose
        private String checkEmail;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("remember_token")
        @Expose
        private Object rememberToken;
        @SerializedName("access_code")
        @Expose
        private Object accessCode;
        @SerializedName("organisation_name")
        @Expose
        private Object organisationName;
        @SerializedName("department")
        @Expose
        private Object department;
        @SerializedName("designation")
        @Expose
        private Object designation;
        @SerializedName("emp_id")
        @Expose
        private Object empId;
        @SerializedName("linked_id_url")
        @Expose
        private Object linkedIdUrl;
        @SerializedName("business_card")
        @Expose
        private Object businessCard;
        @SerializedName("onboarding_status")
        @Expose
        private String onboardingStatus;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("user_images")
        @Expose
        private Object userImages;
        @SerializedName("google_healthkit_status")
        @Expose
        private String googleHealthkitStatus;
        @SerializedName("zoho_id")
        @Expose
        private String zohoId;
        @SerializedName("zoho_id_encrypt")
        @Expose
        private Object zohoIdEncrypt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_by")
        @Expose
        private Object createdBy;
        @SerializedName("updated_by")
        @Expose
        private Object updatedBy;
        @SerializedName("create_mode")
        @Expose
        private Object createMode;
        @SerializedName("change_mode")
        @Expose
        private Object changeMode;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getHrId() {
            return hrId;
        }

        public void setHrId(Integer hrId) {
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

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
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

        public String getPhoneNumberEncrypt() {
            return phoneNumberEncrypt;
        }

        public void setPhoneNumberEncrypt(String phoneNumberEncrypt) {
            this.phoneNumberEncrypt = phoneNumberEncrypt;
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

        public String getEmailEncrypt() {
            return emailEncrypt;
        }

        public void setEmailEncrypt(String emailEncrypt) {
            this.emailEncrypt = emailEncrypt;
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

        public Object getRememberToken() {
            return rememberToken;
        }

        public void setRememberToken(Object rememberToken) {
            this.rememberToken = rememberToken;
        }

        public Object getAccessCode() {
            return accessCode;
        }

        public void setAccessCode(Object accessCode) {
            this.accessCode = accessCode;
        }

        public Object getOrganisationName() {
            return organisationName;
        }

        public void setOrganisationName(Object organisationName) {
            this.organisationName = organisationName;
        }

        public Object getDepartment() {
            return department;
        }

        public void setDepartment(Object department) {
            this.department = department;
        }

        public Object getDesignation() {
            return designation;
        }

        public void setDesignation(Object designation) {
            this.designation = designation;
        }

        public Object getEmpId() {
            return empId;
        }

        public void setEmpId(Object empId) {
            this.empId = empId;
        }

        public Object getLinkedIdUrl() {
            return linkedIdUrl;
        }

        public void setLinkedIdUrl(Object linkedIdUrl) {
            this.linkedIdUrl = linkedIdUrl;
        }

        public Object getBusinessCard() {
            return businessCard;
        }

        public void setBusinessCard(Object businessCard) {
            this.businessCard = businessCard;
        }

        public String getOnboardingStatus() {
            return onboardingStatus;
        }

        public void setOnboardingStatus(String onboardingStatus) {
            this.onboardingStatus = onboardingStatus;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Object getUserImages() {
            return userImages;
        }

        public void setUserImages(Object userImages) {
            this.userImages = userImages;
        }

        public String getGoogleHealthkitStatus() {
            return googleHealthkitStatus;
        }

        public void setGoogleHealthkitStatus(String googleHealthkitStatus) {
            this.googleHealthkitStatus = googleHealthkitStatus;
        }

        public String getZohoId() {
            return zohoId;
        }

        public void setZohoId(String zohoId) {
            this.zohoId = zohoId;
        }

        public Object getZohoIdEncrypt() {
            return zohoIdEncrypt;
        }

        public void setZohoIdEncrypt(Object zohoIdEncrypt) {
            this.zohoIdEncrypt = zohoIdEncrypt;
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

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Object getCreateMode() {
            return createMode;
        }

        public void setCreateMode(Object createMode) {
            this.createMode = createMode;
        }

        public Object getChangeMode() {
            return changeMode;
        }

        public void setChangeMode(Object changeMode) {
            this.changeMode = changeMode;
        }

    }


    public class OfficeLocation {

        @SerializedName("office_lat")
        @Expose
        private Object officeLat;
        @SerializedName("office_lon")
        @Expose
        private Object officeLon;

        public Object getOfficeLat() {
            return officeLat;
        }

        public void setOfficeLat(Object officeLat) {
            this.officeLat = officeLat;
        }

        public Object getOfficeLon() {
            return officeLon;
        }

        public void setOfficeLon(Object officeLon) {
            this.officeLon = officeLon;
        }

    }




}

