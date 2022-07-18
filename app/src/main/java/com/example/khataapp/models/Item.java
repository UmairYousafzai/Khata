package com.example.khataapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.khataapp.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item extends BaseObservable implements Parcelable {

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
    @SerializedName("CtnSize")
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

    private int btn_action;
    @SerializedName("Qty")
    @Expose
    @Bindable
    private double qty = 0;
    @SerializedName("FreeQty")
    @Expose

    private double freeQty;
    @SerializedName("Scheme")
    @Expose
    private double scheme;
    @SerializedName("Cost")
    @Expose
    @Bindable
    private double cost;
    @SerializedName("Discount")
    @Expose
    private double discount;
    @SerializedName("Amount")
    @Expose
    private double amount;
    @SerializedName("FreeCtn")
    @Expose
    @Bindable
    private String freeCtn = "";
    @SerializedName("CtnQtY")
    @Expose
    @Bindable
    private String ctnQtY = "";

    @Bindable
    private double schemeQty;

    public static boolean IS_PURCHASE = true;

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
        btn_action = in.readInt();
        qty = in.readDouble();
        freeQty = in.readDouble();
        scheme = in.readDouble();
        cost = in.readDouble();
        discount = in.readDouble();
        amount = in.readDouble();
        freeCtn = in.readString();
        ctnQtY = in.readString();
        schemeQty = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(barcode);
        dest.writeString(businessBarcode);
        dest.writeString(businessID);
        dest.writeString(userID);
        dest.writeString(referenceCode);
        dest.writeString(uan);
        dest.writeStringList(uanList);
        dest.writeString(departmentCode);
        dest.writeString(groupCode);
        dest.writeString(subGroupCode);
        dest.writeString(description);
        dest.writeString(nativeDescription);
        dest.writeDouble(unitCost);
        dest.writeDouble(unitRetail);
        dest.writeDouble(ctnPcs);
        dest.writeString(supplierCode);
        dest.writeString(productType);
        dest.writeString(comments);
        dest.writeString(status);
        dest.writeString(productImage);
        dest.writeString(gstType);
        dest.writeString(action);
        dest.writeDouble(gstPercentage);
        dest.writeString(hsCode);
        dest.writeDouble(hsPercentage);
        dest.writeDouble(gstAmount);
        dest.writeDouble(netCost);
        dest.writeDouble(unitSize);
        dest.writeInt(btn_action);
        dest.writeDouble(qty);
        dest.writeDouble(freeQty);
        dest.writeDouble(scheme);
        dest.writeDouble(cost);
        dest.writeDouble(discount);
        dest.writeDouble(amount);
        dest.writeString(freeCtn);
        dest.writeString(ctnQtY);
        dest.writeDouble(schemeQty);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        if (this.qty != qty) {
            this.qty = qty;

            setCtnQtY(calculateCtnQty(qty));
            if (this.qty >= scheme) {
                setSchemeQty(calculateSchemeQty(qty));
            }
            if (IS_PURCHASE) {
                setAmount(qty * unitCost);
            } else {
                setAmount(qty * unitRetail);
            }


            notifyPropertyChanged(BR.qty);
        }
    }

    private double calculateSchemeQty(double qty) {
        int noOfScheme = (int) (qty / scheme);
        int freeItems = (int) (noOfScheme * freeQty);

        return freeItems;

    }


    public double getFreeQty() {
        return freeQty;
    }

    public void setFreeQty(double freeQty) {
        this.freeQty = freeQty;

    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if (this.cost != cost) {
            this.cost = cost;
            notifyPropertyChanged(BR.cost);
        }
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {

        this.amount = amount;
    }

    public int getBtn_action() {
        return btn_action;
    }

    public void setBtn_action(int btn_action) {
        this.btn_action = btn_action;
    }

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
        if (IS_PURCHASE) {
            setAmount(this.qty * unitCost);

        }
    }

    public double getUnitRetail() {

        return unitRetail;
    }

    public void setUnitRetail(double unitRetail) {
        this.unitRetail = unitRetail;
        this.cost = unitRetail;
        if (!IS_PURCHASE) {
            setAmount(qty * unitRetail);
        }
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

    public double getScheme() {
        return scheme;
    }

    public void setScheme(double scheme) {
        this.scheme = scheme;
    }

    public String getFreeCtn() {
        return freeCtn;
    }

    public void setFreeCtn(String freeCtn) {
        if (!this.freeCtn.equals(freeCtn)) {

            this.freeCtn = freeCtn;
        }
        notifyPropertyChanged(BR.freeCtn);
    }

    public String getCtnQtY() {

        return ctnQtY;
    }

    public void setCtnQtY(String ctnQtY) {
        if (!this.ctnQtY.equals(ctnQtY)) {
            this.ctnQtY = ctnQtY;

        }
        notifyPropertyChanged(BR.ctnQtY);
    }

    public double getSchemeQty() {
        return schemeQty;
    }

    public void setSchemeQty(double schemeQty) {
        if (this.schemeQty != schemeQty) {
            this.schemeQty = schemeQty;
            setFreeCtn(calculateSchemeCtnQty(schemeQty));
            notifyPropertyChanged(BR.schemeQty);
        }
    }

    private String calculateCtnQty(double qty) {


        int ctn = 0;
        int pcs = 0;
        if (ctnPcs != 0.0) {
            ctn = ((int) qty / (int) ctnPcs);
            pcs = ((int) qty % (int) ctnPcs);
        }

        return ctn + "-" + pcs;
    }

    private String calculateSchemeCtnQty(double qty) {

        int ctn = 0;
        int pcs = 0;
        if (ctnPcs != 0.0) {
            ctn = ((int) qty / (int) ctnPcs);
            pcs = ((int) qty % (int) ctnPcs);
        }

        return ctn + "-" + pcs;
    }
}
