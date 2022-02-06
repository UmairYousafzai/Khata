package com.example.khataapp.stock.viewmodel;

import static com.example.khataapp.utils.CONSTANTS.GET_DEPARTMENT;
import static com.example.khataapp.utils.CONSTANTS.GET_SUPPLIER;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_RESPONSE;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.models.Department;
import com.example.khataapp.models.GetDepartmentResponse;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.ServerResponse;
import com.example.khataapp.stock.repository.StockRepository;
import com.example.khataapp.utils.CONSTANTS;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.List;

public class StockViewModel extends AndroidViewModel {

    private final StockRepository stockRepository;
    private MutableLiveData<List<Department>> departmentMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Party>> partyMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ServerResponse> serverLiveData = new MutableLiveData<>();
    private MutableLiveData<String> serverErrorLiveData = new MutableLiveData<>();

    public StockViewModel(@NonNull Application application) {
        super(application);

        stockRepository = StockRepository.getInstance(application);
    }

    public MutableLiveData<ServerResponse> saveItem(Item item) {
        stockRepository.saveItemToServer(item);
        getServerResponse();
        return serverLiveData;
    }

    public MutableLiveData<List<Department>> getDepartment() {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        stockRepository.getDepartmentFromServer(businessID);
        getServerResponse();
        return departmentMutableLiveData;
    }

    public MutableLiveData<List<Party>> getParty() {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        stockRepository.getSupplier(businessID);
        getServerResponse();
        return partyMutableLiveData;
    }


    public void getServerResponse() {
        stockRepository.setCallBackListener(new StockRepository.CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {

                if (object != null) {
                    if (key == SERVER_RESPONSE) {
                        ServerResponse serverResponse = (ServerResponse) object;
                        serverLiveData.setValue(serverResponse);
                    } else if (key == GET_DEPARTMENT) {

                        GetDepartmentResponse getDepartmentResponse = (GetDepartmentResponse) object;
                        List<Department> list = getDepartmentResponse.getDepartmentList();
                        departmentMutableLiveData.setValue(list);

                    } else if (key == SERVER_ERROR) {
                        String error = (String) object;

                        serverErrorLiveData.setValue(error);
                    }else if (key == GET_SUPPLIER) {

                        List<Party> partyList= (List<Party>) object;
                        partyMutableLiveData.setValue(partyList);
                    }
                }

            }
        });

    }


}
