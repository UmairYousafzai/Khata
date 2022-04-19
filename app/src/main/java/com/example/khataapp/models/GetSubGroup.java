package com.example.khataapp.models;

import com.example.khataapp.models.response.ServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSubGroup extends ServerResponse {

    @SerializedName("Data")
    private List<SubGroup> subGroupList;

    public List<SubGroup> getSubGroupList() {
        return subGroupList;
    }

    public void setSubGroupList(List<SubGroup> subGroupList) {
        this.subGroupList = subGroupList;
    }
}
