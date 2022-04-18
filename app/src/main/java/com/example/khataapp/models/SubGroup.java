package com.example.khataapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubGroup {
    @SerializedName("DepartmentCode")
    @Expose
    private String departmentCode;
    @SerializedName("GroupCode")
    @Expose
    private String groupCode;
    @SerializedName("SubGroupCode")
    @Expose
    private String subGroupCode;
    @SerializedName("SubGroupName")
    @Expose
    private String subGroupName;

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("BusinessId")
    @Expose
    private String businessId;
    @SerializedName("Action")
    @Expose
    private String action;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getSubGroupCode() {
        return subGroupCode;
    }

    public void setSubGroupCode(String subGroupCode) {
        this.subGroupCode = subGroupCode;
    }

    public String getSubGroupName() {
        return subGroupName;
    }

    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }
}
