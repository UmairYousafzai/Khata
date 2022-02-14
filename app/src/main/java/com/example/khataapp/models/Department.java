package com.example.khataapp.models;

import androidx.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("DepartmentCode")
    @Expose
    private String departmentCode;
    @SerializedName("DepartmentName")
    @Expose
    private String departmentName;

    @SerializedName("UserId")
    @Expose
    private String userID;

    @SerializedName("BusinessId")
    @Expose
    private String businessID;

    @SerializedName("Action")
    @Expose
    private String action;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {

        return departmentName;
    }

    public void setDepartmentName(String departmentName) {

            this.departmentName = departmentName;


    }
}
