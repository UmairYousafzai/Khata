package com.example.khataapp.models.response.party;

import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.response.ServerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavePartyResponse extends ServerResponse {

    @SerializedName("Data")
    @Expose
    private Party party;

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
