package com.example.khataapp.models.response.stockAdjustment;

import com.example.khataapp.models.request.SaveStockAdjustmentRequest;
import com.example.khataapp.models.response.ServerResponse;

public class SaveStockAdjustmentResponse extends ServerResponse {

    private SaveStockAdjustmentRequest Data=null;

    public SaveStockAdjustmentRequest getData() {
        return Data;
    }

    public void setData(SaveStockAdjustmentRequest data) {
        Data = data;
    }
}
