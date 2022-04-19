package com.example.khataapp.models;

import com.example.khataapp.models.response.ServerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDocumentByCode extends ServerResponse {

    @SerializedName("Data")
    @Expose
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
