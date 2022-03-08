package com.example.khataapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SaveDocumentResponse extends ServerResponse{

    @SerializedName("Data")
    @Expose
    private Document document;

    public Document getPurchase() {
        return document;
    }

    public void setPurchase(Document document) {
        this.document = document;
    }
}
