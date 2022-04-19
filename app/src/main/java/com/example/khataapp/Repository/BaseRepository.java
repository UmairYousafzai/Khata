package com.example.khataapp.Repository;

import com.example.khataapp.Interface.CallBackListener;

public abstract class BaseRepository {
    protected CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }
}
