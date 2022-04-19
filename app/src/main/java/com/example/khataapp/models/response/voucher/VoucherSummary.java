package com.example.khataapp.models.response.voucher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoucherSummary {
    @SerializedName("VoucherNo")
    @Expose
    private String voucherNo;
    @SerializedName("DebitAmount")
    @Expose
    private double debitAmount;
    @SerializedName("CreditAmount")
    @Expose
    private double creditAmount;
    @SerializedName("TotalAmount")
    @Expose
    private double totalAmount;

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
