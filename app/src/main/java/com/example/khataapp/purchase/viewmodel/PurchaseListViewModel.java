package com.example.khataapp.purchase.viewmodel;

import static com.example.khataapp.utils.CONSTANTS.GET_DOCUMENT;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.GetDocumentResponse;
import com.example.khataapp.models.Document;
import com.example.khataapp.purchase.adapter.PurchaseListAdapter;
import com.example.khataapp.purchase.repository.PurchaseRepository;
import com.example.khataapp.utils.SharedPreferenceHelper;

public class PurchaseListViewModel extends AndroidViewModel {

    private final PurchaseRepository repository;
    private final PurchaseListAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Document> purchaseMutableLiveData;


    public PurchaseListViewModel(@NonNull Application application) {
        super(application);
        repository = new PurchaseRepository();
        adapter= new PurchaseListAdapter(this,null,1);
        toastMessage= new MutableLiveData<>();
        purchaseMutableLiveData= new MutableLiveData<>();
    }

    public void onClick(Document document)
    {
        if (document.getStatus().equals("Unauthorize"))
        {
            purchaseMutableLiveData.setValue(document);

        }
        else
        {
            toastMessage.setValue("Authorized Documents Cannot be Edit.");
        }
    }

    public MutableLiveData<Document> getPurchaseMutableLiveData() {
        return purchaseMutableLiveData;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public PurchaseListAdapter getAdapter() {
        return adapter;
    }

    public void getPurchasesList()
    {
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getPurchases(businessID);
        getServerResponse();
    }

    private void getServerResponse() {

        repository.setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object!=null)
                {
                    if (key== GET_DOCUMENT)
                    {
                        GetDocumentResponse purchaseResponse= (GetDocumentResponse) object;

                        adapter.setPurchaseList(purchaseResponse.getPurchaseList());
                    }
                    else if (key== SERVER_ERROR)
                    {
                        toastMessage.setValue((String) object);
                    }
                }
            }
        });
    }
}
