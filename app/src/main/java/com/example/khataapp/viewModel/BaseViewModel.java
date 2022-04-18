package com.example.khataapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public abstract class BaseViewModel extends AndroidViewModel {

    protected MutableLiveData<String> toastMessage;
    protected MutableLiveData<Boolean> showProgressDialog;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        toastMessage= new MutableLiveData<>();
        showProgressDialog= new MutableLiveData<>();
    }


    protected void setShowProgressDialog(Boolean show)
    {
        showProgressDialog.setValue(show);
    }

    protected void setToastMessage(String message)
    {
        toastMessage.setValue(message);
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public MutableLiveData<Boolean> getShowProgressDialog() {
        return showProgressDialog;
    }
}
