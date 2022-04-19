package com.example.khataapp.models.response.voucher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoucherData {
    @SerializedName("Summary")
    @Expose
    private VoucherSummary voucherSummary;
    @SerializedName("Detail")
    @Expose
    private List<VoucherDetail> detail = null;

    public VoucherSummary getVoucherSummary() {
        return voucherSummary;
    }

    public void setVoucherSummary(VoucherSummary voucherSummary) {
        this.voucherSummary = voucherSummary;
    }

    public List<VoucherDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<VoucherDetail> detail) {
        this.detail = detail;
    }
}
