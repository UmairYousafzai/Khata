package com.example.khataapp.views.purchase.viewmodel;

import static com.example.khataapp.utils.CONSTANTS.GET_ITEMS;
import static com.example.khataapp.utils.CONSTANTS.GET_DOCUMENT_BY_CODE;
import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SAVE_BTN;
import static com.example.khataapp.utils.CONSTANTS.SAVE_DOCUMENT_RESPONSE;
import static com.example.khataapp.utils.CONSTANTS.SAVE_PURCHASE_RETURN_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.GetItemResponse;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.GetDocumentByCode;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.SaveDocumentResponse;
import com.example.khataapp.utils.DateUtil;
import com.example.khataapp.views.purchase.adapter.ProductRecyclerAdapter;
import com.example.khataapp.views.purchase.repository.PurchaseRepository;
import com.example.khataapp.utils.Converter;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PurchaseViewModel extends AndroidViewModel {

    private final PurchaseRepository repository;
    private final MutableLiveData<Integer> btnAction;
    private final MutableLiveData<Party> party;
    private final MutableLiveData<Item> itemMutableLiveData;
    private final ProductRecyclerAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Boolean> isEdit;
    private final MutableLiveData<Boolean> showProgressDialog;
    private final ObservableField<String> date;
    private final ObservableField<ArrayAdapter<String>> supplierAdapter;
    private final HashMap<String, String> supplierHashMap;
    private final ObservableField<String> selectedSupplierName;
    private final ObservableField<ArrayAdapter<String>> productAdapter;
    private final HashMap<String, String> productHashMap;
    private final ObservableField<String> selectedProductName;
    private String supplierCode, productBarCode, productName = "";
    private List<Item> itemList;
    private final ObservableField<String> totalQty;
    private final ObservableField<String> subTotalAmount;
    private final ObservableField<String> totalAmount;
    private final ObservableField<String> gstTax;
    private final ObservableField<Boolean> gstFlag;
    private final ObservableField<String> docNumber;
    private boolean gstOldFlg = false;
    private final MutableLiveData<String> actionMutableLiveData;


    public PurchaseViewModel(@NonNull Application application) {
        super(application);
        Item.IS_PURCHASE=true;

        repository = new PurchaseRepository();
        btnAction = new MutableLiveData<>();
        party = new MutableLiveData<>();
        isEdit = new MutableLiveData<>();
        showProgressDialog = new MutableLiveData<>();
        itemMutableLiveData = new MutableLiveData<>();
        actionMutableLiveData = new MutableLiveData<>("INSERT");
        adapter = new ProductRecyclerAdapter(this,null,1);
        toastMessage = new MutableLiveData<>();
        String formatDate=DateUtil.getInstance().getDate();
        date = new ObservableField<>(formatDate);
        totalQty = new ObservableField<>("0");
        subTotalAmount = new ObservableField<>("0");
        supplierAdapter = new ObservableField<>();
        selectedSupplierName = new ObservableField<>();
        supplierHashMap = new HashMap<>();
        productAdapter = new ObservableField<>();
        selectedProductName = new ObservableField<>("");
        totalAmount = new ObservableField<>("");
        gstTax = new ObservableField<>("");
        gstFlag = new ObservableField<>(false);
        docNumber = new ObservableField<>("------");
        productHashMap = new HashMap<>();
        itemList = new ArrayList<>();
        getSuppliers();
        getProducts();

    }

    public void onClick(int key) {

        if (key == SAVE_BTN) {
            showProgressDialog.setValue(true);
            savePurchase();
        } else {
            btnAction.setValue(key);
        }
    }


    public void onClickRemove(Item item) {
        if (item != null) {
            adapter.removeItem(item);
            isEdit.setValue(true);

        }
    }

    public MutableLiveData<String> getActionMutableLiveData() {
        return actionMutableLiveData;
    }

    public MutableLiveData<Boolean> getShowProgressDialog() {
        return showProgressDialog;
    }

    public ObservableField<Boolean> getGstFlag() {

        if (gstOldFlg != gstFlag.get()) {
            if (gstFlag.get()) {
                try {
                    double subAmount = Double.parseDouble(subTotalAmount.get());
                    double gstPercentage = (17 * subAmount) / 100;
                    gstTax.set(String.valueOf(gstPercentage));
                    double amount = Double.parseDouble(totalAmount.get());
                    amount += gstPercentage;
                    totalAmount.set(String.valueOf(amount));
                } catch (Exception e) {
                    Log.e("PurchaseViewModel:", e.toString());
                }
            } else {
                try {
                    double subAmount = Double.parseDouble(subTotalAmount.get());
                    double gstPercentage = (17 * subAmount) / 100;
                    gstTax.set("0.0");
                    double amount = Double.parseDouble(totalAmount.get());
                    amount -= gstPercentage;
                    totalAmount.set(String.valueOf(amount));
                } catch (Exception e) {
                    Log.e("PurchaseViewModel:", e.toString());
                }
            }
        }

        gstOldFlg = gstFlag.get();
        return gstFlag;
    }

    public ObservableField<String> getTotalAmount() {
        return totalAmount;
    }

    public ObservableField<String> getGstTax() {
        return gstTax;
    }

    public MutableLiveData<Boolean> getIsEdit() {
        return isEdit;
    }

    public ObservableField<String> getSubTotalAmount() {
        return subTotalAmount;
    }

    public ObservableField<String> getTotalQty() {
        return totalQty;
    }

    public ObservableField<String> getDate() {
        return date;
    }



    public void addItemToProductAdapter() {
        isEdit.setValue(true);

        if (!adapter.checkItemExists(itemMutableLiveData.getValue())) {
            Item item = new Item();
            item = itemMutableLiveData.getValue();
            if (item != null) {


                try {
                    double qty = Item.totalQty;
                    double amount = Item.totalAmount;

                    totalQty.set(String.valueOf(qty));
                    subTotalAmount.set(String.valueOf(amount));
                    if (gstFlag.get()) {
                        double subAmount = Double.parseDouble(subTotalAmount.get());
                        double gstPercentage = (17 * subAmount) / 100;
                        gstTax.set(String.valueOf(gstPercentage));
                        double tAmount = Double.parseDouble(totalAmount.get());
                        tAmount += gstPercentage;
                        totalAmount.set(String.valueOf(tAmount));
                    } else {
                        totalAmount.set(String.valueOf(amount));
                    }
                } catch (Exception e) {
                    Log.e("ConverSion error:", e.getMessage());
                }

            }


            adapter.addItem(itemMutableLiveData.getValue());

        } else {
            toastMessage.setValue("Item already Exists");
        }
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
        if (selectedProductName != null && !productName.equals(selectedProductName.get())) {
            productName = selectedProductName.get();
            productBarCode = productHashMap.get(selectedProductName.get());

            for (Item model : itemList) {
                if (model.getBarcode().equals(productBarCode)) {
                    model.setCost(model.getUnitRetail());
                    if (!adapter.checkItemExists(model)) {
                        itemMutableLiveData.setValue(model);
                    } else {
                        toastMessage.setValue("Item Already Selected");
                    }
                    break;
                }
            }

        }
        return selectedProductName;
    }

    public ObservableField<String> getSelectedSupplierName() {
        if (selectedSupplierName.get() != null && !selectedSupplierName.get().isEmpty()) {
            supplierCode = supplierHashMap.get(selectedSupplierName.get());
            isEdit.setValue(true);

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


    public void clearItemList() {
        adapter.clearList();
    }


    private void getSuppliers() {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getPartiesFromServer("s", businessID);
        showProgressDialog.setValue(true);
        getServerResponse();
    }

    private void getProducts() {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getItems(businessID);
        showProgressDialog.setValue(true);

        getServerResponse();
    }

    public ObservableField<String> getDocNumber() {
        return docNumber;
    }

    public void getPurchaseByDocCode(String docCode)
    {
        repository.getPurchaseByCode(docCode);
        getServerResponse();
        showProgressDialog.setValue(true);

    }
    private void setFields(Document document)
    {
        docNumber.set(document.getDocNo());
        date.set(Converter.StringToFormatDate(document.getDocDate()));
        selectedSupplierName.set(document.getSupplierName());
        totalAmount.set(String.valueOf(document.getTotalAmount()));
        subTotalAmount.set(String.valueOf(document.getTotalAmount()));
        adapter.setItemList(document.getItems());
        actionMutableLiveData.setValue("UPDATE");
        for (Item item: document.getItems())
        {
            Item.totalQty+=item.getQty();
            Item.totalAmount+=item.getAmount();
        }
        totalQty.set(String.valueOf(Item.totalQty));

    }

    private void savePurchase() {
        if (!date.get().isEmpty()) {
            if (!supplierCode.isEmpty()) {
                if (Item.totalAmount != 0) {
                    String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
                    String userID = SharedPreferenceHelper.getInstance(getApplication()).getUserID();
                    Document document = new Document();
                    document.setItems(adapter.getItemList());
                    document.setAction(actionMutableLiveData.getValue());
                    document.setBusinessId(businessID);
                    document.setLocationCode("000001");
                    document.setSupplierCode(supplierCode);
                    document.setTotalAmount(Double.parseDouble(totalAmount.get()));
                    document.setUserId(userID);
                    document.setStatus("0");
                    document.setDocDate(date.get());
                    if (actionMutableLiveData.getValue().equals("UPDATE"))
                    {
                        document.setDocNo(docNumber.get());
                    }
                    else
                    {
                        document.setDocNo("");

                    }

                    showProgressDialog.setValue(true);
                    repository.savePurchase(document);

                } else {
                    toastMessage.setValue("Please Enter Products");
                }
            } else {
                toastMessage.setValue("Please select Supplier");
            }
        } else {
            toastMessage.setValue("Please select Date");
        }

    }

    private void getServerResponse() {

        repository.setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object != null) {
                    if (key == GET_PARTY) {
                        GetPartyServerResponse partyServerResponse = (GetPartyServerResponse) object;
                        setUpSupplierSpinner(partyServerResponse.getPartyList());
                        showProgressDialog.setValue(false);


                    } else if (key == GET_ITEMS) {
                        GetItemResponse itemResponse = (GetItemResponse) object;
                        itemList = itemResponse.getItem();
                        setupProductSpinner(itemResponse.getItem());
                        showProgressDialog.setValue(false);


                    } else if (key == SERVER_ERROR) {
                        toastMessage.setValue((String) object);
                        showProgressDialog.setValue(false);


                    }else if (key == SAVE_DOCUMENT_RESPONSE) {
                        SaveDocumentResponse saveDocumentResponse = (SaveDocumentResponse) object;
                        if (saveDocumentResponse.getCode() == 200) {
                            isEdit.setValue(false);
                        }
                        showProgressDialog.setValue(false);
                        actionMutableLiveData.setValue("Update");
                        toastMessage.setValue(saveDocumentResponse.getMessage());
                    }else if (key == GET_DOCUMENT_BY_CODE) {
                        GetDocumentByCode purchaseByCode = (GetDocumentByCode) object;
                        if (purchaseByCode.getCode() == 200) {

                            setFields(purchaseByCode.getDocument());

                        }


                        toastMessage.setValue(purchaseByCode.getMessage());
                        showProgressDialog.setValue(false);

                    }
                }

            }
        });
    }

    private void setupProductSpinner(List<Item> list) {

        List<String> productNameList = new ArrayList<>();

        for (Item model : list) {
            productNameList.add(model.getDescription());
            productHashMap.put(model.getDescription(), model.getBarcode());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, productNameList);

        productAdapter.set(adapter);
    }

    private void setUpSupplierSpinner(List<Party> list) {

        List<String> supplierNameList = new ArrayList<>();

        for (Party model : list) {
            supplierNameList.add(model.getPartyName());
            supplierHashMap.put(model.getPartyName(), model.getPartyCode());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, supplierNameList);

        supplierAdapter.set(adapter);

    }
}
