package com.example.khataapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item implements Parcelable {

    @SerializedName("Barcode")
    @Expose
    private String barcode;
    @SerializedName("BusinessBarcode")
    @Expose
    private String businessBarcode;

    @SerializedName("BusinessId")
    @Expose
    private String businessID;

    @SerializedName("UserId")
    @Expose
    private String userID;
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

    @SerializedName("Action")
    @Expose
    private String action;

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
    @SerializedName("UNIT_SIZE")
    @Expose
    private double unitSize;


    public Item() {
    }


    protected Item(Parcel in) {
        barcode = in.readString();
        businessBarcode = in.readString();
        businessID = in.readString();
        userID = in.readString();
        referenceCode = in.readString();
        uan = in.readString();
        uanList = in.createStringArrayList();
        departmentCode = in.readString();
        groupCode = in.readString();
        subGroupCode = in.readString();
        description = in.readString();
        nativeDescription = in.readString();
        unitCost = in.readDouble();
        unitRetail = in.readDouble();
        ctnPcs = in.readDouble();
        supplierCode = in.readString();
        productType = in.readString();
        comments = in.readString();
        status = in.readString();
        productImage = in.readString();
        gstType = in.readString();
        action = in.readString();
        gstPercentage = in.readDouble();
        hsCode = in.readString();
        hsPercentage = in.readDouble();
        gstAmount = in.readDouble();
        netCost = in.readDouble();
        unitSize = in.readDouble();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public double getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(double unitSize) {
        this.unitSize = unitSize;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(barcode);
        parcel.writeString(businessBarcode);
        parcel.writeString(businessID);
        parcel.writeString(userID);
        parcel.writeString(referenceCode);
        parcel.writeString(uan);
        parcel.writeStringList(uanList);
        parcel.writeString(departmentCode);
        parcel.writeString(groupCode);
        parcel.writeString(subGroupCode);
        parcel.writeString(description);
        parcel.writeString(nativeDescription);
        parcel.writeDouble(unitCost);
        parcel.writeDouble(unitRetail);
        parcel.writeDouble(ctnPcs);
        parcel.writeString(supplierCode);
        parcel.writeString(productType);
        parcel.writeString(comments);
        parcel.writeString(status);
        parcel.writeString(productImage);
        parcel.writeString(gstType);
        parcel.writeString(action);
        parcel.writeDouble(gstPercentage);
        parcel.writeString(hsCode);
        parcel.writeDouble(hsPercentage);
        parcel.writeDouble(gstAmount);
        parcel.writeDouble(netCost);
        parcel.writeDouble(unitSize);
    }
}
