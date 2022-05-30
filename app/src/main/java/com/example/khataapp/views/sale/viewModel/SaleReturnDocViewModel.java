package com.example.khataapp.views.sale.viewModel;

import static com.example.khataapp.utils.CONSTANTS.AUTHORIZE_BTN;
import static com.example.khataapp.utils.CONSTANTS.GET_DOCUMENT_BY_CODE;
import static com.example.khataapp.utils.CONSTANTS.GET_ITEMS;
import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SAVE_BTN;
import static com.example.khataapp.utils.CONSTANTS.SAVE_DOCUMENT_RESPONSE;
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
import com.example.khataapp.models.GetPartiesServerResponse;
import com.example.khataapp.models.GetDocumentByCode;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.SaveDocumentResponse;
import com.example.khataapp.utils.DateUtil;
import com.example.khataapp.views.sale.adapter.ProductSaleRecyclerAdapter;
import com.example.khataapp.views.sale.repository.SaleRepository;
import com.example.khataapp.utils.Converter;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaleReturnDocViewModel extends AndroidViewModel {



    private final SaleRepository repository;
    private final MutableLiveData<Integer> btnAction;
    private final MutableLiveData<Party> party;
    private final MutableLiveData<Item> itemMutableLiveData;
    private final ProductSaleRecyclerAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Boolean> isEdit;
    private final MutableLiveData<Boolean> showProgressDialog;
    private final ObservableField<String> date;
    private final ObservableField<ArrayAdapter<String>> customerAdapter;
    private final HashMap<String, String> customerHashMap;
    private final ObservableField<String> selectedCustomerName;
    private final ObservableField<ArrayAdapter<String>> productAdapter;
    private final HashMap<String, String> productHashMap;
    private final ObservableField<String> selectedProductName;
    private String customerCode="", productBarCode, productName = "";
    private List<Item> itemList;
    private final ObservableField<String> totalQty;
    private final ObservableField<String> subTotalAmount;
    private final ObservableField<String> totalAmount;
    private final ObservableField<String> gstTax;
    private final ObservableField<Boolean> gstFlag;
    private final ObservableField<String> docNumberBusiness;
    private String docNumber="";
    private boolean gstOldFlg = false;
    private final MutableLiveData<String> actionMutableLiveData;
    private boolean isAuthorizeRequest=false;


    public SaleReturnDocViewModel(@NonNull Application application) {
        super(application);
        Item.IS_PURCHASE=false;
        repository = new SaleRepository();
        btnAction = new MutableLiveData<>();
        party = new MutableLiveData<>();
        isEdit = new MutableLiveData<>();
        showProgressDialog = new MutableLiveData<>();
        itemMutableLiveData = new MutableLiveData<>();
        actionMutableLiveData = new MutableLiveData<>("INSERT");
        adapter = new ProductSaleRecyclerAdapter(this,null,2);
        toastMessage = new MutableLiveData<>();
        date = new ObservableField<>(DateUtil.getInstance().getDate());
        totalQty = new ObservableField<>("0");
        subTotalAmount = new ObservableField<>("0");
        customerAdapter = new ObservableField<>();
        selectedCustomerName = new ObservableField<>();
        customerHashMap = new HashMap<>();
        productAdapter = new ObservableField<>();
        selectedProductName = new ObservableField<>("");
        totalAmount = new ObservableField<>("0");
        gstTax = new ObservableField<>("");
        gstFlag = new ObservableField<>(false);
        docNumberBusiness = new ObservableField<>("------");
        productHashMap = new HashMap<>();
        itemList = new ArrayList<>();
        getCustomer();
        getProducts();

    }

    public void onClick(int key) {

        if (key == SAVE_BTN) {
            showProgressDialog.setValue(true);
            saveSaleDocument("0");
        } else if(key==AUTHORIZE_BTN)
        {
            isAuthorizeRequest=true;
            showProgressDialog.setValue(true);
            saveSaleDocument("3");
        }
        else {
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
                    double qty = Double.parseDouble(totalQty.get())+item.getQty();
                    double amount = Double.parseDouble(totalAmount.get())+item.getAmount();

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
                    Log.e("Conversion error:", e.getMessage());
                }
            }
            adapter.addItem(itemMutableLiveData.getValue());
        } else {
            toastMessage.setValue("Item already Exists");
        }
    }


    public void addItemToProductAdapter(Item item) {
        if (item != null) {
            try {
                double qty = Double.parseDouble(totalQty.get()) + item.getQty();
                double amount = Double.parseDouble(totalAmount.get()) + item.getAmount();
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
                Log.e("Conversion error:", e.getMessage());
            }
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

    public ObservableField<String> getSelectedCustomerName() {
        if (selectedCustomerName.get() != null && !selectedCustomerName.get().isEmpty()) {
            customerCode = customerHashMap.get(selectedCustomerName.get());
            isEdit.setValue(true);

        }
        return selectedCustomerName;
    }

    public ObservableField<ArrayAdapter<String>> getCustomerAdapter() {
        return customerAdapter;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public ProductSaleRecyclerAdapter getAdapter() {
        return adapter;
    }


    public MutableLiveData<Integer> getBtnAction() {
        return btnAction;
    }


    public void clearItemList() {
        adapter.clearList();
    }


    private void getCustomer() {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        showProgressDialog.setValue(true);
        repository.getPartiesFromServer("c", businessID);

        getServerResponse();
    }

    private void getProducts() {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        showProgressDialog.setValue(true);

        repository.getItems(businessID);

        getServerResponse();
    }

    public ObservableField<String> getDocNumberBusiness() {
        return docNumberBusiness;
    }

    public void getPurchaseByDocCode(String docCode)
    {
        repository.getSaleDocByCode(docCode);
        getServerResponse();
    }

    private void setFields(Document document) {
        docNumberBusiness.set(document.getDocNoBusinessWise());
        docNumber=document.getDocNo();
        date.set(Converter.StringToFormatDate(document.getDocDate()));
        selectedCustomerName.set(document.getPartyName());
        totalAmount.set(String.valueOf(document.getTotalAmount()));
        subTotalAmount.set(String.valueOf(document.getTotalAmount()));
        adapter.setAuthenticate(document.getStatus().equals("3"));
        adapter.setItemList(document.getItems());
        actionMutableLiveData.setValue("UPDATE");
        customerCode=document.getSupplierCode();

        double quantity=0;
        for (Item item : document.getItems()) {
            quantity+=item.getQty();
        }
        totalQty.set(String.valueOf(quantity));

    }


    private void saveSaleDocument(String authorizeKey) {
        if (!date.get().isEmpty()) {
            if (!customerCode.isEmpty()) {
                if (!totalAmount.get().equals("0")) {
                    String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
                    String userID = SharedPreferenceHelper.getInstance(getApplication()).getUserID();
                    Document document = new Document();
                    document.setItems(adapter.getItemList());
                    document.setAction(actionMutableLiveData.getValue());
                    document.setBusinessId(businessID);
                    document.setLocationCode("000001");
                    document.setDocType("4");
                    document.setPartyCode(customerCode);
                    document.setTotalAmount(Double.parseDouble(totalAmount.get()));
                    document.setUserId(userID);
                    document.setStatus(authorizeKey);
                    document.setDocDate(date.get());
                    if (actionMutableLiveData.getValue().equals("UPDATE"))
                    {
                        document.setDocNo(docNumberBusiness.get());
                        document.setDocNo(docNumber);
                    }
                    showProgressDialog.setValue(true);

                    repository.saveSaleDocument(document);

                } else {
                    showProgressDialog.setValue(false);

                    toastMessage.setValue("Please Enter Products");
                }
            } else {
                showProgressDialog.setValue(false);

                toastMessage.setValue("Please select Customer");
            }
        } else {
            showProgressDialog.setValue(false);

            toastMessage.setValue("Please select Date");
        }

    }

    private void getServerResponse() {

        repository.setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object != null) {
                    if (key == GET_PARTY) {
                        GetPartiesServerResponse partyServerResponse = (GetPartiesServerResponse) object;
                        setUpCustomerSpinner(partyServerResponse.getPartyList());
                        showProgressDialog.setValue(false);

                    } else if (key == GET_ITEMS) {
                        GetItemResponse itemResponse = (GetItemResponse) object;
                        itemList = itemResponse.getItem();
                        setupProductSpinner(itemResponse.getItem());
                        showProgressDialog.setValue(false);


                    } else if (key == SERVER_ERROR) {
                        toastMessage.setValue((String) object);
                        actionMutableLiveData.setValue("INSERT");
                        showProgressDialog.setValue(false);
                        isAuthorizeRequest=false;

                    } else if (key == SAVE_DOCUMENT_RESPONSE) {
                        SaveDocumentResponse saveDocumentResponse = (SaveDocumentResponse) object;
                        if (saveDocumentResponse.getCode() == 200) {
                            if (isAuthorizeRequest)
                            {
                                isAuthorizeRequest=true;
                                clearData();
                            }
                            else
                            {
                                isEdit.setValue(false);
                                docNumberBusiness.set(saveDocumentResponse.getDocument().getDocNoBusinessWise());
                                docNumber= saveDocumentResponse.getDocument().getDocNo();
                                actionMutableLiveData.setValue("UPDATE");
                            }

                        }

                        toastMessage.setValue(saveDocumentResponse.getMessage());
                        showProgressDialog.setValue(false);

                    }else if (key == GET_DOCUMENT_BY_CODE) {
                        GetDocumentByCode purchaseByCode = (GetDocumentByCode) object;
                        if (purchaseByCode.getCode() == 200) {

                            setFields(purchaseByCode.getDocument());

                        }

                        showProgressDialog.setValue(false);
                        toastMessage.setValue(purchaseByCode.getMessage());
                    }
                }

            }
        });
    }
    private void clearData() {
        docNumber="";
        docNumberBusiness.set("");
        selectedCustomerName.set("");
        adapter.clearList();
        itemList.clear();
        totalAmount.set("0");
        totalQty.set("0");
        subTotalAmount.set("0");
        gstTax.set("0");
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

    private void setUpCustomerSpinner(List<Party> list) {

        List<String> customerNameList = new ArrayList<>();

        for (Party model : list) {
            customerNameList.add(model.getPartyName());
            customerHashMap.put(model.getPartyName(), model.getPartyCode());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, customerNameList);
        customerAdapter.set(adapter);

    }
}
