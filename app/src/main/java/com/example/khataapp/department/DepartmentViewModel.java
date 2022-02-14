package com.example.khataapp.department;

import static com.example.khataapp.utils.CONSTANTS.GET_DEPARTMENT;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_RESPONSE;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.Department;
import com.example.khataapp.models.GetDepartmentResponse;
import com.example.khataapp.utils.CONSTANTS;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DepartmentViewModel extends AndroidViewModel {

    private ArrayAdapter<String> departmentAdapter;
    private final MutableLiveData<String> serverErrorMutableLiveData;
    private final HashMap<String, String> departmentHashMap;
    private final MutableLiveData<ArrayAdapter<String>> departmentAdapterMutableLiveData;
    private final MutableLiveData<Integer> progressMutableLiveData;
    private List<Department> departmentList = new ArrayList<>();
    private ObservableField<String> selectedDepartmentName;
    private ObservableField<String> editDepartmentName;
    private ObservableField<Boolean> visibility;
    private String departmentID = "";
    private String action="";
    private final MutableLiveData<String> toastMessage;


    public DepartmentViewModel(@NonNull Application application) {
        super(application);
        serverErrorMutableLiveData = new MutableLiveData<>();
        departmentHashMap = new HashMap<>();
        departmentAdapterMutableLiveData = new MutableLiveData<>();
        progressMutableLiveData = new MutableLiveData<>();
        selectedDepartmentName = new ObservableField<>();
        editDepartmentName = new ObservableField<>();
        visibility = new ObservableField<>();
        toastMessage= new MutableLiveData<>();
    }


    public ObservableField<String> getEditDepartmentName() {
        return editDepartmentName;
    }

    public void setEditDepartmentName(ObservableField<String> editDepartmentName) {
        this.editDepartmentName = editDepartmentName;
    }

    public ObservableField<Boolean> getVisibility() {
        return visibility;
    }

    public void setVisibility(ObservableField<Boolean> visibility) {
        this.visibility = visibility;
    }

    public ObservableField<String> getSelectedDepartmentName() {
        return selectedDepartmentName;
    }

    public void setSelectedDepartmentName(ObservableField<String> selectedDepartmentName) {
        this.selectedDepartmentName = selectedDepartmentName;
        departmentID = departmentHashMap.get(selectedDepartmentName.get());
    }

    public void getDepartmentList() {
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

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    private void getServerResponse() {

        DepartmentRepository.getInstance().setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object != null) {
                    if (key == GET_DEPARTMENT) {

                        GetDepartmentResponse getDepartmentResponse = (GetDepartmentResponse) object;
                        departmentList = getDepartmentResponse.getDepartmentList();

                        setUpSpinner(departmentList);
                        progressMutableLiveData.setValue(1);

                    } else if (key == SERVER_ERROR) {
                        String error = (String) object;

                        serverErrorMutableLiveData.setValue(error);
                        progressMutableLiveData.setValue(1);

                    }
                    else if (key==SERVER_RESPONSE)
                    {

                    }
                }
            }
        });
    }

    private void setUpSpinner(List<Department> list) {

        List<String> departmentNameList = new ArrayList<>();
        for (Department model : list) {
            departmentNameList.add(model.getDepartmentName());
            departmentHashMap.put(model.getDepartmentName(), model.getDepartmentCode());
        }

        departmentAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, departmentNameList);

        departmentAdapterMutableLiveData.setValue(departmentAdapter);
    }

    public void onClick(int key) {
        if (key == CONSTANTS.ADD_BTN) {
            visibility.set(true);
            editDepartmentName.set("");
            selectedDepartmentName.set("");
            departmentID = "";
            action="INSERT";
        } else if (key == CONSTANTS.EDIT_BTN) {
            visibility.set(true);
            editDepartmentName.set(selectedDepartmentName.get());
            action="UPDATE";
            action="";
        } else if (key == CONSTANTS.SAVE_BTN) {
            visibility.set(false);
            saveDepartment();
            editDepartmentName.set("");
        }

    }

    private void saveDepartment() {
        if (!action.isEmpty())
        {
            String userID= SharedPreferenceHelper.getInstance(getApplication()).getUserID();
            String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
            Department department = new Department();
            department.setDepartmentCode(departmentID);
            department.setDepartmentName(editDepartmentName.get());
            department.setBusinessID(businessID);
            department.setUserID(userID);
            department.setAction(action);
        }
        else
        {
            toastMessage.setValue("Please Select Action\n Edit or Add");
        }


    }

}
