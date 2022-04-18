package com.example.khataapp.models;

import com.google.gson.annotations.SerializedName;

public class SaveSubGroupResponse extends ServerResponse{

    @SerializedName("Data")
    private SubGroup subGroup;

    public SubGroup getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(SubGroup subGroup) {
        this.subGroup = subGroup;
    }
}
