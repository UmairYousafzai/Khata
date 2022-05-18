package com.example.khataapp.views.purchase.viewmodel;

import static com.example.khataapp.utils.CONSTANTS.GET_DOCUMENT;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.GetDocumentResponse;
import com.example.khataapp.models.Document;
import com.example.khataapp.views.purchase.adapter.PurchaseListAdapter;
import com.example.khataapp.views.purchase.repository.PurchaseRepository;
import com.example.khataapp.utils.SharedPreferenceHelper;

public class PurchaseListViewModel extends AndroidViewModel {

    private final PurchaseRepository repository;
    private final PurchaseListAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Document> purchaseMutableLiveData;
    private final MutableLiveData<Boolean> showProgressDialog;



    public PurchaseListViewModel(@NonNull Application application) {
        super(application);
        repository = new PurchaseRepository();
        adapter= new PurchaseListAdapter(this);
        toastMessage= new MutableLiveData<>();
        showProgressDialog= new MutableLiveData<>();
        purchaseMutableLiveData= new MutableLiveData<>();
    }

    public void onClick(Document document)
    {

            purchaseMutableLiveData.setValue(document);


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

    public MutableLiveData<Boolean> getShowProgressDialog() {
        return showProgressDialog;
    }

    public void getPurchasesList()
    {
        showProgressDialog.setValue(true);
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getPurchasesList(businessID);
        getServerResponse();
    }
   public void getPurchasesReturnList()
    {
        showProgressDialog.setValue(true);
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getPurchaseReturnList(businessID);
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
                        showProgressDialog.setValue(false);

                    }
                    else if (key== SERVER_ERROR)
                    {
                        toastMessage.setValue((String) object);
                        showProgressDialog.setValue(false);

                    }
                }
            }
        });
    }
}
