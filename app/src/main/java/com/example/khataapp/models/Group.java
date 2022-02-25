package com.example.khataapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("DepartmentCode")
    @Expose
    private String departmentCode;
    @SerializedName("BusinessId")
    @Expose
    private String businessID;
    @SerializedName("UserId")
    @Expose
    private String userID;
    @SerializedName("GroupCode")
    @Expose
    private String groupCode;
    @SerializedName("GroupName")
    @Expose
    private String groupName;

    @SerializedName("Action")
    @Expose
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDepartmentCode() {

        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
