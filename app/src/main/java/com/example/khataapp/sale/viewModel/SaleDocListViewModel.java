package com.example.khataapp.sale.viewModel;

import static com.example.khataapp.utils.CONSTANTS.GET_DOCUMENT;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.GetDocumentResponse;
import com.example.khataapp.purchase.adapter.PurchaseListAdapter;
import com.example.khataapp.sale.repository.SaleRepository;
import com.example.khataapp.utils.SharedPreferenceHelper;

public class SaleDocListViewModel extends AndroidViewModel {


    private final SaleRepository repository;
    private final PurchaseListAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Document> documentMutableLiveData;


    public SaleDocListViewModel(@NonNull Application application) {
        super(application);
        repository = new SaleRepository();
        adapter= new PurchaseListAdapter(null,this,2);
        toastMessage= new MutableLiveData<>();
        documentMutableLiveData = new MutableLiveData<>();
    }

    public void onClick(Document document)
    {
        if (document.getStatus().equals("Unauthorize"))
        {
            documentMutableLiveData.setValue(document);

        }
        else
        {
            toastMessage.setValue("Authorized Documents Cannot be Edit.");
        }
    }

    public MutableLiveData<Document> getDocumentMutableLiveData() {
        return documentMutableLiveData;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public PurchaseListAdapter getAdapter() {
        return adapter;
    }

    public void getDocument()
    {
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getSaleDocs(2,businessID);
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
