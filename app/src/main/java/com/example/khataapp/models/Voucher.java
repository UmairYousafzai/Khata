package com.example.khataapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Voucher {

    @SerializedName("PartyCode")
    @Expose
    private String partyCode;
    @SerializedName("VoucherType")
    @Expose
    private String voucherType;
    @SerializedName("VoucherDate")
    @Expose
    private String voucherDate;
    @SerializedName("VoucherAmount")
    @Expose
    private double voucherAmount;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("RefDoc")
    @Expose
    private String refDoc;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("BusinessId")
    @Expose
    private String businessId;
    @SerializedName("Action")
    @Expose
    private String action;

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public double getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(double voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRefDoc() {
        return refDoc;
    }

    public void setRefDoc(String refDoc) {
        this.refDoc = refDoc;
    }

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

}