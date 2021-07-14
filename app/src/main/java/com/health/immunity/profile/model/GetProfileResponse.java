package com.health.immunity.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetProfileResponse implements Serializable {

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


    public class HomeLocation implements Serializable {

        @SerializedName("home_lat")
        @Expose
        private String homeLat;
        @SerializedName("home_lon")
        @Expose
        private String homeLon;
        private final static long serialVersionUID = 5047439806098115884L;

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


    public class JsonData implements Serializable {

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
        @SerializedName("personal_email")
        @Expose
        private String personal_email;
        @SerializedName("email_encrypt")
        @Expose
        private String emailEncrypt;

        public String getPersonal_email() {
            return personal_email;
        }

        public void setPersonal_email(String personal_email) {
            this.personal_email = personal_email;
        }

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
        private String userImages;
        @SerializedName("verified_community_photo_link")
        @Expose
        private String commImages;
        @SerializedName("zoho_id")
        @Expose
        private String zohoId;
        @SerializedName("zoho_id_encrypt")
        @Expose
        private String zohoIdEncrypt;
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
        @SerializedName("organization")
        @Expose
        private String organization;
        @SerializedName("extended_invit_count")
        @Expose
        private Integer extendedInvitCount;
        @SerializedName("user_address")
        @Expose
        private UserAddress userAddress;
        private final static long serialVersionUID = 528422609762725988L;

        public Integer getId() {
            System.out.println(+id);
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

        public String getUserImages() {
            return userImages;
        }
        public String getCommImages() {
            return commImages;
        }

        public void setUserImages(String userImages) {
            this.userImages = userImages;
        }

        public String getZohoId() {
            return zohoId;
        }

        public void setZohoId(String zohoId) {
            this.zohoId = zohoId;
        }

        public String getZohoIdEncrypt() {
            return zohoIdEncrypt;
        }

        public void setZohoIdEncrypt(String zohoIdEncrypt) {
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

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public Integer getExtendedInvitCount() {
            return extendedInvitCount;
        }

        public void setExtendedInvitCount(Integer extendedInvitCount) {
            this.extendedInvitCount = extendedInvitCount;
        }

        public UserAddress getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(UserAddress userAddress) {
            this.userAddress = userAddress;
        }

    }


    public class OfficeLocation implements Serializable {

        @SerializedName("office_lat")
        @Expose
        private double officeLat;
        @SerializedName("office_lon")
        @Expose
        private double officeLon;
        private final static long serialVersionUID = 6566008907973771634L;

        public double getOfficeLat() {
            return officeLat;
        }

        public void setOfficeLat(double officeLat) {
            this.officeLat = officeLat;
        }

        public double getOfficeLon() {
            return officeLon;
        }

        public void setOfficeLon(double officeLon) {
            this.officeLon = officeLon;
        }
    }


    public class UserAddress implements Serializable {

        @SerializedName("office_lat")
        @Expose
        private String officeLat;
        @SerializedName("office_lon")
        @Expose
        private String officeLon;
        @SerializedName("home_lat")
        @Expose
        private String homeLat;
        @SerializedName("home_lon")
        @Expose
        private String homeLon;
        @SerializedName("home_address")
        @Expose
        private String homeAddress;
        @SerializedName("office_address")
        @Expose
        private String officeAddress;
        private final static long serialVersionUID = 8735914919499125908L;

        public String getOfficeLat() {
            return officeLat;
        }

        public void setOfficeLat(String officeLat) {
            this.officeLat = officeLat;
        }

        public String getOfficeLon() {
            return officeLon;
        }

        public void setOfficeLon(String officeLon) {
            this.officeLon = officeLon;
        }

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

        public String getHomeAddress() {
            return homeAddress;
        }

        public void setHomeAddress(String homeAddress) {
            this.homeAddress = homeAddress;
        }

        public String getOfficeAddress() {
            return officeAddress;
        }

        public void setOfficeAddress(String officeAddress) {
            this.officeAddress = officeAddress;
        }

    }
}
