package com.example.khataapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity
public class User {
    @PrimaryKey (autoGenerate = true)
    private  int userID_pk;

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("UserPassword")
    @Expose
    private String userPassword;
    @SerializedName("LocationId")
    @Expose
    private String locationId;
    @SerializedName("LocationName")
    @Expose
    private String locationName;
    @SerializedName("BusinessId")
    @Expose
    private String businessId;
    @SerializedName("BusinessName")
    @Expose
    private String businessName;

    public int getUserID_pk() {
        return userID_pk;
    }

    public void setUserID_pk(int userID_pk) {
        this.userID_pk = userID_pk;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
