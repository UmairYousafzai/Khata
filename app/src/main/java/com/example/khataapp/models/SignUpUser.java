package com.example.khataapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpUser {
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Business")
    @Expose
    private String business;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("LocationCode")
    @Expose
    private String locationCode;

    public SignUpUser() {
    }

    public SignUpUser(String userName, String mobile, String email, String address, String business, String password, String locationCode) {
        this.userName = userName;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.business = business;
        this.password = password;
        this.locationCode = locationCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}
