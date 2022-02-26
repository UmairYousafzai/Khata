package com.example.khataapp.purchase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.purchase.adapter.ProductRecyclerAdapter;

import java.util.List;

public class PurchaseViewModel extends AndroidViewModel  {

    private final MutableLiveData<Integer> btnAction;
    private final ObservableField<Party> party;
    private final ProductRecyclerAdapter adapter;


    public PurchaseViewModel(@NonNull Application application) {
        super(application);
        btnAction= new MutableLiveData<>();
        party = new ObservableField<>();
        adapter = new ProductRecyclerAdapter();
    }

    public void onClick(int key)
    {

            btnAction.setValue(key);

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
}
