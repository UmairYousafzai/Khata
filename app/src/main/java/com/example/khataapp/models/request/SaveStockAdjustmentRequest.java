package com.example.khataapp.models.request;

import com.example.khataapp.models.Item;
import com.example.khataapp.models.response.ServerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveStockAdjustmentRequest {
        @SerializedName("DocNo")
        @Expose
        private String docNo;
        @SerializedName("DocNoBusinesWise")
        @Expose
        private String docNoBusinessWise;
        @SerializedName("DocType")
        @Expose
        private String docType;
        @SerializedName("DocDate")
        @Expose
        private String docDate;
        @SerializedName("LocationCode")
        @Expose
        private String locationCode;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("TotalAmount")
        @Expose
        private double totalAmount;
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("BusinessId")
        @Expose
        private String businessId;
        @SerializedName("Action")
        @Expose
        private String action;
        @SerializedName("DocumentDetail")
        @Expose
        private List<Item> documentDetail = null;

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getDocNoBusinessWise() {
        return docNoBusinessWise;
    }

    public void setDocNoBusinessWise(String docNoBusinessWise) {
        this.docNoBusinessWise = docNoBusinessWise;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public List<Item> getDocumentDetail() {
        return documentDetail;
    }

    public void setDocumentDetail(List<Item> documentDetail) {
        this.documentDetail = documentDetail;
    }
}
