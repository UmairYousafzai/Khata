package com.example.khataapp.models.response.voucher;

import com.example.khataapp.models.response.ServerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoucherResponse extends ServerResponse {

    @SerializedName("Data")
    @Expose
    private VoucherData voucherData;

    public VoucherData getVoucherData() {
        return voucherData;
    }

    public void setVoucherData(VoucherData voucherData) {
        this.voucherData = voucherData;
    }
}
