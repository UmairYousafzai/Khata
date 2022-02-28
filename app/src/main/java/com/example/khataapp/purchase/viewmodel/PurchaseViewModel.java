package com.example.khataapp.purchase.viewmodel;

import static com.example.khataapp.utils.CONSTANTS.GET_ITEMS;
import static com.example.khataapp.utils.CONSTANTS.GET_SUPPLIER;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.GetItemResponse;
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
    private final MutableLiveData<Party> party;
    private final MutableLiveData<Item> itemMutableLiveData;
    private final ProductRecyclerAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Integer> dialogDismiss;
    private final ObservableField<ArrayAdapter<String>> supplierAdapter;
    private final HashMap<String, String> supplierHashMap;
    private final ObservableField<String> selectedSupplierName;
    private final ObservableField<ArrayAdapter<String>> productAdapter;
    private final HashMap<String, String> productHashMap;
    private final ObservableField<String> selectedProductName;
    private String supplierCode,productBarCode;
    private List<Item> itemList;


    public PurchaseViewModel(@NonNull Application application) {
        super(application);

        repository= new PurchaseRepository();
        btnAction= new MutableLiveData<>();
        party = new MutableLiveData<>();
        itemMutableLiveData = new MutableLiveData<>();
        adapter = new ProductRecyclerAdapter();
        toastMessage= new MutableLiveData<>();
        dialogDismiss= new MutableLiveData<>();
        supplierAdapter= new ObservableField<>();
        selectedSupplierName= new ObservableField<>();
        supplierHashMap= new HashMap<>();
        productAdapter= new ObservableField<>();
        selectedProductName= new ObservableField<>();
        productHashMap= new HashMap<>();
        itemList= new ArrayList<>();
        getSuppliers();
        getProducts();

    }

    public void onClick(int key)
    {

        if (key!=10&&key!=11)
        {
            btnAction.setValue(key);

        }
        else
        {
            if (key==10)
            {
                if (!adapter.checkItemExists(itemMutableLiveData.getValue()))
                {
                    adapter.addItem(itemMutableLiveData.getValue());

                }
                else
                {
                    toastMessage.setValue("Item already Exists");
                }

            }
            dialogDismiss.setValue(1);

        }

    }

    public MutableLiveData<Integer> getDialogDismiss() {
        return dialogDismiss;
    }

    public MutableLiveData<Item> getItemMutableLiveData() {
        return itemMutableLiveData;
    }

    public MutableLiveData<Party> getParty() {
        return party;
    }

    public ObservableField<ArrayAdapter<String>> getProductAdapter() {
        return productAdapter;
    }

    public ObservableField<String> getSelectedProductName() {
        if (selectedProductName!=null)
        {
            productBarCode= productHashMap.get(selectedProductName.get());

            for (Item model:itemList)
            {
                if (model.getBarcode().equals(productBarCode))
                {
                    itemMutableLiveData.setValue(model);
                    break;
                }
            }

        }
        return selectedProductName;
    }

    public ObservableField<String> getSelectedSupplierName() {
        if (selectedSupplierName!=null)
        {
            supplierCode= supplierHashMap.get(selectedSupplierName.get());
        }
        return selectedSupplierName;
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



    public MutableLiveData<Integer> getBtnAction() {
        return btnAction;
    }




    private void getSuppliers()
    {
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getPartiesFromServer("s",businessID);

        getServerResponse();
    }

    private void getProducts()
    {
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getItems(businessID);

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
                    else if (key== GET_ITEMS)
                    {
                        GetItemResponse itemResponse= (GetItemResponse) object;
                        itemList= itemResponse.getItem();
                        setupProductSpinner(itemResponse.getItem());

                    }
                    else if (key==SERVER_ERROR)
                    {
                        toastMessage.setValue((String) object);
                    }
                }

            }
        });
    }

    private void setupProductSpinner(List<Item> list) {

        List<String> productNameList= new ArrayList<>();

        for (Item model:list)
        {
            productNameList.add(model.getDescription());
            productHashMap.put(model.getDescription(), model.getBarcode());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item,productNameList);

        productAdapter.set(adapter);
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