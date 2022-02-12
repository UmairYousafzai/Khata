package com.example.khataapp.department;

import static com.example.khataapp.utils.CONSTANTS.GET_DEPARTMENT;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.Department;
import com.example.khataapp.models.GetDepartmentResponse;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

public class DepartmentViewModel extends AndroidViewModel {

    private ArrayAdapter<String> departmentAdapter;
    private final MutableLiveData<String> serverErrorMutableLiveData;
    private final HashMap<String,String> departmentHashMap;
    private final MutableLiveData<ArrayAdapter<String>> departmentAdapterMutableLiveData;
    private final MutableLiveData<Integer> progressMutableLiveData;
    private ObservableField<String>  departmentName=new ObservableField<>();


    public DepartmentViewModel(@NonNull Application application) {
        super(application);
        serverErrorMutableLiveData= new MutableLiveData<>();
        departmentHashMap= new HashMap<>();
        departmentAdapterMutableLiveData = new MutableLiveData<>();
        progressMutableLiveData= new MutableLiveData<>();
    }

    public String getDepartmentName()
    {
        return departmentName.get();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }

    public void getDepartment()
    {
        String businessId = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        getServerResponse();

        DepartmentRepository.getInstance().getDepartmentFromServer(businessId);
    }

    public MutableLiveData<ArrayAdapter<String>> getDepartmentAdapter() {
        return departmentAdapterMutableLiveData;
    }



    public MutableLiveData<String> getServerErrorMutableLiveData() {
        return serverErrorMutableLiveData;
    }

    public MutableLiveData<Integer> getProgressMutableLiveData() {
        return progressMutableLiveData;
    }

    private void getServerResponse() {

         DepartmentRepository.getInstance().setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object!=null)
                {
                     if (key == GET_DEPARTMENT) {

                        GetDepartmentResponse getDepartmentResponse = (GetDepartmentResponse) object;
                        List<Department> list = getDepartmentResponse.getDepartmentList();

                        setUpSpinner(list);
                        progressMutableLiveData.setValue(1);

                    } else if (key == SERVER_ERROR) {
                        String error = (String) object;

                        serverErrorMutableLiveData.setValue(error);
                         progressMutableLiveData.setValue(1);

                     }
                }
            }
        });
    }

    private void setUpSpinner(List<Department> list) {

        List<String> departmentNameList= new ArrayList<>();
        for (Department model:list)
        {
            departmentNameList.add(model.getDepartmentName());
            departmentHashMap.put(model.getDepartmentName(),model.getDepartmentCode());
        }

        departmentAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item,departmentNameList);

        departmentAdapterMutableLiveData.setValue(departmentAdapter);
    }

}
