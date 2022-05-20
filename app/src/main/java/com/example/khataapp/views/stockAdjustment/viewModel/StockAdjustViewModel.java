package com.example.khataapp.views.stockAdjustment.viewModel;

import static com.example.khataapp.utils.CONSTANTS.AUTHORIZE_BTN;
import static com.example.khataapp.utils.CONSTANTS.GET_ITEMS;
import static com.example.khataapp.utils.CONSTANTS.GET_STOCK_ADJUSTMENT_BY_CODE;
import static com.example.khataapp.utils.CONSTANTS.SAVE_BTN;
import static com.example.khataapp.utils.CONSTANTS.SAVE_STOCK_ADJUSTMENT_RESPONSE;
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
import com.example.khataapp.models.GetDocumentByCode;
import com.example.khataapp.models.GetItemResponse;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.SaveDocumentResponse;
import com.example.khataapp.models.request.SaveStockAdjustmentRequest;
import com.example.khataapp.models.response.stockAdjustment.GetStockAdjustByCode;
import com.example.khataapp.models.response.stockAdjustment.GetStockAdjustResponse;
import com.example.khataapp.models.response.stockAdjustment.SaveStockAdjustmentResponse;
import com.example.khataapp.utils.Converter;
import com.example.khataapp.utils.DateUtil;
import com.example.khataapp.utils.SharedPreferenceHelper;
import com.example.khataapp.views.stockAdjustment.adapter.StockAdjustProductAdapter;
import com.example.khataapp.views.stockAdjustment.reposiory.StockAdjustRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockAdjustViewModel extends AndroidViewModel
{
    public final static String TAG= StockAdjustViewModel.class.getSimpleName();
    private final StockAdjustRepository repository;
    private final MutableLiveData<Integer> btnAction;
    private final MutableLiveData<Party> party;
    private final MutableLiveData<Item> itemMutableLiveData;
    private final StockAdjustProductAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Boolean> isEdit;
    private final MutableLiveData<Boolean> showProgressDialog;
    private final ObservableField<String> date;
    private final ObservableField<ArrayAdapter<String>> typeAdapter;
    private final HashMap<String, String> typeHashMap;
    private final ObservableField<String> selectedType;
    private final ObservableField<ArrayAdapter<String>> productAdapter;
    private final HashMap<String, String> productHashMap;
    private final ObservableField<String> selectedProductName;
    private String type ="", productBarCode, productName = "";
    private List<Item> itemList;
    private final ObservableField<String> totalQty;
    private final ObservableField<String> subTotalAmount;
    private final ObservableField<String> totalAmount;
    private final ObservableField<String> gstTax;
    private final ObservableField<Boolean> gstFlag;
    private final ObservableField<String> docNumber;
    private boolean gstOldFlg = false;
    private final MutableLiveData<String> actionMutableLiveData;
    private boolean isAuthorizeRequest=false;



    public StockAdjustViewModel(@NonNull Application application) {
        super(application);
        Item.IS_PURCHASE = true;
        repository = new StockAdjustRepository();
        btnAction = new MutableLiveData<>();
        party = new MutableLiveData<>();
        isEdit = new MutableLiveData<>();
        showProgressDialog = new MutableLiveData<>();
        itemMutableLiveData = new MutableLiveData<>();
        actionMutableLiveData = new MutableLiveData<>("INSERT");
        adapter = new StockAdjustProductAdapter(this);
        toastMessage = new MutableLiveData<>();
        String formatDate = DateUtil.getInstance().getDate();
        date = new ObservableField<>(formatDate);
        totalQty = new ObservableField<>("0");
        subTotalAmount = new ObservableField<>("0");
        typeAdapter = new ObservableField<>();
        selectedType = new ObservableField<>();
        typeHashMap = new HashMap<>();
        productAdapter = new ObservableField<>();
        selectedProductName = new ObservableField<>("");
        totalAmount = new ObservableField<>("0");
        gstTax = new ObservableField<>("");
        gstFlag = new ObservableField<>(false);
        docNumber = new ObservableField<>("------");
        productHashMap = new HashMap<>();
        itemList = new ArrayList<>();
        getProducts();
        setUpTypeSpinner();

    }

    public void onClick(int key) {

        if (key == SAVE_BTN) {
            showProgressDialog.setValue(true);
            savePurchase("0");
        } else if (key == AUTHORIZE_BTN) {
            isAuthorizeRequest=true;
            showProgressDialog.setValue(true);
            savePurchase("3");
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
                    Log.e(TAG+":", e.toString());
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
                    Log.e("ConverSion error:", e.getMessage());
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

    public ObservableField<String> getSelectedType() {
        if (selectedType.get() != null && !selectedType.get().isEmpty()) {
            type = typeHashMap.get(selectedType.get())!=null?typeHashMap.get(selectedType.get()):"";
            isEdit.setValue(true);

        }
        return selectedType;
    }

    public ObservableField<ArrayAdapter<String>> getTypeAdapter() {
        return typeAdapter;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public StockAdjustProductAdapter getAdapter() {
        return adapter;
    }


    public MutableLiveData<Integer> getBtnAction() {
        return btnAction;
    }


    public void clearItemList() {
        adapter.clearList();
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

    public void getStockAdjustmentByDocCode(String docCode) {
        repository.getStockAdjustmentByCode(docCode);
        getServerResponse();
        showProgressDialog.setValue(true);

    }

    private void setFields(SaveStockAdjustmentRequest document) {
        docNumber.set(document.getDocNo());
        date.set(Converter.StringToFormatDate(document.getDocDate()));
        totalAmount.set(String.valueOf(document.getTotalAmount()));
        subTotalAmount.set(String.valueOf(document.getTotalAmount()));
        adapter.setAuthenticate(document.getStatus().equals("3"));

        adapter.setItemList(document.getDocumentDetail());
        actionMutableLiveData.setValue("UPDATE");
        if (document.getStatus().equals("W"))
        {
            selectedType.set("Waste");

        }
        else
        {
            selectedType.set("Gain");

        }
        type = document.getStatus()!=null?document.getStatus():"";

        double quantity = 0;
        for (Item item : document.getDocumentDetail()) {
            quantity += item.getQty();
        }
        totalQty.set(String.valueOf(quantity));

    }

    private void savePurchase(String authorizeKey) {
        if (!date.get().isEmpty()) {
            if (!type.isEmpty()) {
                if (!totalAmount.get().equals("0")) {
                    String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
                    String userID = SharedPreferenceHelper.getInstance(getApplication()).getUserID();
                    SaveStockAdjustmentRequest document = new SaveStockAdjustmentRequest();
                    document.setDocumentDetail(adapter.getItemList());
                    document.setAction(actionMutableLiveData.getValue());
                    document.setBusinessId(businessID);
                    document.setLocationCode("000001");
                    document.setDocType(getDocType());
                    document.setTotalAmount(Double.parseDouble(totalAmount.get()));
                    document.setUserId(userID);
                    document.setStatus(authorizeKey);
                    document.setDocDate(date.get());
                    if (actionMutableLiveData.getValue().equals("UPDATE")) {
                        document.setDocNo(docNumber.get());
                    } else {
                        document.setDocNo("");

                    }

                    showProgressDialog.setValue(true);
                    repository.saveStockAdjustment(document);

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
                  if (key == GET_ITEMS) {
                        GetItemResponse itemResponse = (GetItemResponse) object;
                        itemList = itemResponse.getItem();
                        setupProductSpinner(itemResponse.getItem());
                        showProgressDialog.setValue(false);


                    } else if (key == SERVER_ERROR) {
                        toastMessage.setValue((String) object);
                        showProgressDialog.setValue(false);
                        isAuthorizeRequest=false;
                    } else if (key == SAVE_STOCK_ADJUSTMENT_RESPONSE) {
                        SaveStockAdjustmentResponse saveDocumentResponse = (SaveStockAdjustmentResponse) object;
                        if (saveDocumentResponse.getCode() == 200) {
                            if (isAuthorizeRequest)
                            {
                                isAuthorizeRequest=false;
                                clearData();
                            }
                            else
                            {
                                isEdit.setValue(false);
                                docNumber.set(saveDocumentResponse.getData().getDocNoBusinessWise());
                                actionMutableLiveData.setValue("UPDATE");
                            }

                        }
                        showProgressDialog.setValue(false);

                        toastMessage.setValue(saveDocumentResponse.getMessage());
                    } else if (key == GET_STOCK_ADJUSTMENT_BY_CODE) {
                      GetStockAdjustByCode purchaseByCode = (GetStockAdjustByCode) object;
                        if (purchaseByCode.getCode() == 200) {

                            setFields(purchaseByCode.getData());

                        }


                        toastMessage.setValue(purchaseByCode.getMessage());
                        showProgressDialog.setValue(false);

                    }
                }

            }
        });
    }

    private void clearData() {
        docNumber.set("");
        selectedType.set("");
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

    private void setUpTypeSpinner() {

        List<String> typeList = new ArrayList<>();
        typeList.add("Gain");
        typeHashMap.put("Gain","G");
        typeList.add("Waste");
        typeHashMap.put("Waste","W");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, typeList);

        typeAdapter.set(adapter);

    }

    private String getDocType() {

        return type;
    }


}
