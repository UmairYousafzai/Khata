package com.example.khataapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Party implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private  int id_pk;

    @SerializedName("PartyCode")
    @Expose
    private String partyCode;
    @SerializedName("PartyName")
    @Expose
    private String partyName;
    @SerializedName("CNIC")
    @Expose
    private String cnic;
    @SerializedName("PartyAddress")
    @Expose
    private String partyAddress;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("PartyType")
    @Expose
    private String partyType;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("BusinessId")
    @Expose
    private String businessId;
    @SerializedName("Action")
    @Expose
    private String action;

    public Party() {
    }

    protected Party(Parcel in) {
        id_pk = in.readInt();
        partyCode = in.readString();
        partyName = in.readString();
        cnic = in.readString();
        partyAddress = in.readString();
        phone = in.readString();
        mobile = in.readString();
        email = in.readString();
        partyType = in.readString();
        userId = in.readString();
        businessId = in.readString();
        action = in.readString();
    }

    public static final Creator<Party> CREATOR = new Creator<Party>() {
        @Override
        public Party createFromParcel(Parcel in) {
            return new Party(in);
        }

        @Override
        public Party[] newArray(int size) {
            return new Party[size];
        }
    };

    public int getId_pk() {
        return id_pk;
    }

    public void setId_pk(int id_pk) {
        this.id_pk = id_pk;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getPartyAddress() {
        return partyAddress;
    }

    public void setPartyAddress(String partyAddress) {
        this.partyAddress = partyAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_pk);
        dest.writeString(partyCode);
        dest.writeString(partyName);
        dest.writeString(cnic);
        dest.writeString(partyAddress);
        dest.writeString(phone);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(partyType);
        dest.writeString(userId);
        dest.writeString(businessId);
        dest.writeString(action);
    }
}
