package com.example.khataapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    @SerializedName("Barcode")
    @Expose
    private String barcode;
    @SerializedName("BusinessBarcode")
    @Expose
    private String businessBarcode;
    @SerializedName("ReferenceCode")
    @Expose
    private String referenceCode;
    @SerializedName("Uan")
    @Expose
    private String uan;
    @SerializedName("UanList")
    @Expose
    private List<String> uanList = null;
    @SerializedName("DepartmentCode")
    @Expose
    private String departmentCode;
    @SerializedName("GroupCode")
    @Expose
    private String groupCode;
    @SerializedName("SubGroupCode")
    @Expose
    private String subGroupCode;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("NativeDescription")
    @Expose
    private String nativeDescription;
    @SerializedName("UnitCost")
    @Expose
    private double unitCost;
    @SerializedName("UnitRetail")
    @Expose
    private double unitRetail;
    @SerializedName("CtnPcs")
    @Expose
    private double ctnPcs;
    @SerializedName("SupplierCode")
    @Expose
    private String supplierCode;
    @SerializedName("ProductType")
    @Expose
    private String productType;
    @SerializedName("Comments")
    @Expose
    private String comments;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("ProductImage")
    @Expose
    private String productImage;
    @SerializedName("GSTType")
    @Expose
    private String gstType;
    @SerializedName("GST_Percentage")
    @Expose
    private double gstPercentage;
    @SerializedName("HsCode")
    @Expose
    private String hsCode;
    @SerializedName("HsPercentage")
    @Expose
    private double hsPercentage;
    @SerializedName("GSTAmount")
    @Expose
    private double gstAmount;
    @SerializedName("NetCost")
    @Expose
    private double netCost;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBusinessBarcode() {
        return businessBarcode;
    }

    public void setBusinessBarcode(String businessBarcode) {
        this.businessBarcode = businessBarcode;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getUan() {
        return uan;
    }

    public void setUan(String uan) {
        this.uan = uan;
    }

    public List<String> getUanList() {
        return uanList;
    }

    public void setUanList(List<String> uanList) {
        this.uanList = uanList;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNativeDescription() {
        return nativeDescription;
    }

    public void setNativeDescription(String nativeDescription) {
        this.nativeDescription = nativeDescription;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getUnitRetail() {
        return unitRetail;
    }

    public void setUnitRetail(double unitRetail) {
        this.unitRetail = unitRetail;
    }

    public double getCtnPcs() {
        return ctnPcs;
    }

    public void setCtnPcs(double ctnPcs) {
        this.ctnPcs = ctnPcs;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getGstType() {
        return gstType;
    }

    public void setGstType(String gstType) {
        this.gstType = gstType;
    }

    public double getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(double gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public double getHsPercentage() {
        return hsPercentage;
    }

    public void setHsPercentage(double hsPercentage) {
        this.hsPercentage = hsPercentage;
    }

    public double getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(double gstAmount) {
        this.gstAmount = gstAmount;
    }

    public double getNetCost() {
        return netCost;
    }

    public void setNetCost(double netCost) {
        this.netCost = netCost;
    }
}
