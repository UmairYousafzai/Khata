package com.example.khataapp.purchase.viewmodel;

import static com.example.khataapp.utils.CONSTANTS.GET_SUPPLIER;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.purchase.adapter.ProductRecyclerAdapter;
import com.example.khataapp.purchase.repository.PurchaseRepository;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PurchaseViewModel extends AndroidViewModel  {

    private PurchaseRepository repository;
    private final MutableLiveData<Integer> btnAction;
    private final ObservableField<Party> party;
    private final ProductRecyclerAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final ObservableField<ArrayAdapter<String>> supplierAdapter;
    private final HashMap<String, String> supplierHashMap;
    private String supplierCode;


    public PurchaseViewModel(@NonNull Application application) {
        super(application);

        repository= new PurchaseRepository();
        btnAction= new MutableLiveData<>();
        party = new ObservableField<>();
        adapter = new ProductRecyclerAdapter();
        toastMessage= new MutableLiveData<>();
        supplierAdapter= new ObservableField<>();
        supplierHashMap= new HashMap<>();
        getSuppliers();

    }

    public void onClick(int key)
    {

            btnAction.setValue(key);

    }

    public ObservableField<ArrayAdapter<String>> getSupplierAdapter() {
        return supplierAdapter;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public ProductRecyclerAdapter getAdapter() {
        return adapter;
    }

    public ObservableField<Party> getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party.set(party);
    }

    public MutableLiveData<Integer> getBtnAction() {
        return btnAction;
    }

    public void setAdapterList(List<Item> list){
        if (list!=null && list.size()>0)
        {
            adapter.setItemList(list);
        }
    }


    public void getSuppliers()
    {
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getPartiesFromServer("s",businessID);

        getServerResponse();
    }

    private void getServerResponse() {

        repository.setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object!=null)
                {
                    if (key== GET_SUPPLIER)
                    {
                        GetPartyServerResponse partyServerResponse = (GetPartyServerResponse) object;
                        setUpSupplierSpinner(partyServerResponse.getPartyList());

                    }
                    else if (key==SERVER_ERROR)
                    {
                        toastMessage.setValue((String) object);
                    }
                }

            }
        });
    }

    private void setUpSupplierSpinner(List<Party> list) {

        List<String> supplierNameList= new ArrayList<>();

        for (Party model:list)
        {
            supplierNameList.add(model.getPartyName());
            supplierHashMap.put(model.getPartyName(), model.getPartyCode());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item,supplierNameList);

        supplierAdapter.set(adapter);

    }
}
