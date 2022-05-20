package com.example.khataapp.views.stockAdjustment.viewModel;

import static com.example.khataapp.utils.CONSTANTS.GET_STOCK_ADJUSTMENT;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.GetDocumentResponse;
import com.example.khataapp.utils.SharedPreferenceHelper;
import com.example.khataapp.views.stockAdjustment.adapter.StockeAdjustListAdapter;
import com.example.khataapp.views.stockAdjustment.reposiory.StockAdjustRepository;

public class StockAdjustmentListViewModel extends AndroidViewModel {

    private final StockAdjustRepository repository;
    private final StockeAdjustListAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Document> stockAdjustMutableLiveData;
    private final MutableLiveData<Boolean> showProgressDialog;



    public StockAdjustmentListViewModel(@NonNull Application application) {
        super(application);
        repository = new StockAdjustRepository();
        adapter= new StockeAdjustListAdapter(this);
        toastMessage= new MutableLiveData<>();
        showProgressDialog= new MutableLiveData<>();
        stockAdjustMutableLiveData = new MutableLiveData<>();
    }

    public void onClick(Document document)
    {

            stockAdjustMutableLiveData.setValue(document);


    }

    public MutableLiveData<Document> getStockAdjustMutableLiveData() {
        return stockAdjustMutableLiveData;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public StockeAdjustListAdapter getAdapter() {
        return adapter;
    }

    public MutableLiveData<Boolean> getShowProgressDialog() {
        return showProgressDialog;
    }

    public void getStockAdjustList()
    {
        showProgressDialog.setValue(true);
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getStockAdjustList(businessID);
        getServerResponse();
    }
   public void getPurchasesReturnList()
    {
        showProgressDialog.setValue(true);
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getStockAdjustList(businessID);
        getServerResponse();
    }

    private void getServerResponse() {

        repository.setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object!=null)
                {
                    if (key== GET_STOCK_ADJUSTMENT)
                    {
                        GetDocumentResponse purchaseResponse= (GetDocumentResponse) object;

                        adapter.setDocumentList(purchaseResponse.getPurchaseList());
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
