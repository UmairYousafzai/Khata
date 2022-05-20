package com.example.khataapp.models.response.stockAdjustment;

import com.example.khataapp.models.request.SaveStockAdjustmentRequest;
import com.example.khataapp.models.response.ServerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStockAdjustResponse extends ServerResponse {
    @SerializedName("Data")
    @Expose
    private List<SaveStockAdjustmentRequest> data = null;

    public List<SaveStockAdjustmentRequest> getData() {
        return data;
    }

    public void setData(List<SaveStockAdjustmentRequest> data) {
        this.data = data;
    }
}
