package com.example.khataapp.views.department;

import static com.example.khataapp.utils.CONSTANTS.ADD_GROUP_BTN;
import static com.example.khataapp.utils.CONSTANTS.ADD_SUB_GROUP_BTN;
import static com.example.khataapp.utils.CONSTANTS.GET_DEPARTMENT;
import static com.example.khataapp.utils.CONSTANTS.GET_GROUP;
import static com.example.khataapp.utils.CONSTANTS.GET_SUB_GROUP;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_RESPONSE_DEPARTMENT;
import static com.example.khataapp.utils.CONSTANTS.SERVER_RESPONSE_GROUP;
import static com.example.khataapp.utils.CONSTANTS.SERVER_RESPONSE_SUB_GROUP;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.Department;
import com.example.khataapp.models.GetDepartmentResponse;
import com.example.khataapp.models.GetGroupResponse;
import com.example.khataapp.models.GetSubGroup;
import com.example.khataapp.models.Group;
import com.example.khataapp.models.SaveDepartmentResponse;
import com.example.khataapp.models.SaveGroupResponse;
import com.example.khataapp.models.SaveSubGroupResponse;
import com.example.khataapp.models.SubGroup;
import com.example.khataapp.utils.CONSTANTS;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DepartmentViewModel extends AndroidViewModel {

    private ArrayAdapter<String> departmentAdapter;
    private ArrayAdapter<String> groupAdapter;
    private ArrayAdapter<String> subGroupAdapter;
    private final MutableLiveData<String> serverErrorMutableLiveData;
    private final HashMap<String, String> departmentHashMap;
    private final HashMap<String, String> groupHashMap;
    private final HashMap<String, String> subGroupHashMap;
    private final MutableLiveData<ArrayAdapter<String>> departmentAdapterMutableLiveData;
    private final MutableLiveData<ArrayAdapter<String>> groupAdapterMutableLiveData;
    private final MutableLiveData<ArrayAdapter<String>> subGroupAdapterMutableLiveData;
    private final MutableLiveData<Boolean> progressMutableLiveData;
    private List<Department> departmentList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();
    private List<SubGroup> subGroupList = new ArrayList<>();
    private ObservableField<String> selectedDepartmentName;
    private ObservableField<String> editDepartmentName;
    private ObservableField<Boolean> departmentEditTextVisibility;
    private ObservableField<String> selectedGroupName;
    private ObservableField<String> editGroupName;
    private ObservableField<Boolean> groupEditTextVisibility;
    private ObservableField<String> selectedSubGroupName;
    private ObservableField<String> editSubGroupName;
    private ObservableField<Boolean> subGroupEditTextVisibility;
    private String departmentID = "";
    private String departmentAction = "";
    private String groupID = "";
    private String groupAction = "";
    private String subGroupID = "";
    private String subGroupAction = "";
    private final MutableLiveData<String> toastMessage;
    private ObservableField<String> departmentError;
    private ObservableBoolean observableBoolean;
    private String departmentName;
    private String groupName;
    private String subGroupName;



    public DepartmentViewModel(@NonNull Application application) {
        super(application);
        serverErrorMutableLiveData = new MutableLiveData<>();
        departmentHashMap = new HashMap<>();
        groupHashMap = new HashMap<>();
        departmentAdapterMutableLiveData = new MutableLiveData<>();
        groupAdapterMutableLiveData = new MutableLiveData<>();
        progressMutableLiveData = new MutableLiveData<>();
        selectedDepartmentName = new ObservableField<>();
        editDepartmentName = new ObservableField<>();
        departmentEditTextVisibility = new ObservableField<>();
        toastMessage = new MutableLiveData<>();
        departmentError = new ObservableField<>();
        observableBoolean = new ObservableBoolean(false);
        selectedGroupName = new ObservableField<>();
        editGroupName = new ObservableField<>();
        groupEditTextVisibility = new ObservableField<>();
        selectedSubGroupName = new ObservableField<>();
        editSubGroupName = new ObservableField<>();
        subGroupEditTextVisibility = new ObservableField<>();
        subGroupHashMap= new HashMap<>();
        subGroupAdapterMutableLiveData= new MutableLiveData<>();
    }


    public ObservableField<String> getSelectedSubGroupName() {

        subGroupName = selectedSubGroupName.get();
        subGroupID = subGroupHashMap.get(subGroupName);
        subGroupEditTextVisibility.set(false);
        return selectedSubGroupName;
    }

    public void setSelectedSubGroupName(ObservableField<String> selectedSubGroupName) {
        this.selectedSubGroupName = selectedSubGroupName;
    }

    public MutableLiveData<ArrayAdapter<String>> getSubGroupAdapterMutableLiveData() {
        return subGroupAdapterMutableLiveData;
    }

    public ObservableField<String> getEditSubGroupName() {
        return editSubGroupName;
    }

    public void setEditSubGroupName(ObservableField<String> editSubGroupName) {
        this.editSubGroupName = editSubGroupName;
    }

    public ObservableField<Boolean> getSubGroupEditTextVisibility() {
        return subGroupEditTextVisibility;
    }

    public void setSubGroupEditTextVisibility(ObservableField<Boolean> subGroupEditTextVisibility) {
        this.subGroupEditTextVisibility = subGroupEditTextVisibility;
    }




    public ObservableField<String> getSelectedGroupName() {
        groupName = selectedGroupName.get();
        groupID = groupHashMap.get(groupName);
        groupEditTextVisibility.set(false);
        selectedSubGroupName.set("");

        getSubGroupList();
        return selectedGroupName;
    }

    public void setSelectedGroupName(ObservableField<String> selectedGroupName) {
        this.selectedGroupName = selectedGroupName;
    }

    public ObservableField<String> getEditGroupName() {
        return editGroupName;
    }

    public void setEditGroupName(ObservableField<String> editGroupName) {
        this.editGroupName = editGroupName;
    }

    public ObservableField<Boolean> getGroupEditTextVisibility() {
        return groupEditTextVisibility;
    }

    public void setGroupEditTextVisibility(ObservableField<Boolean> groupEditTextVisibility) {
        this.groupEditTextVisibility = groupEditTextVisibility;
    }

    public ObservableBoolean getObservableBoolean() {
        return observableBoolean;
    }

    public void setObservableBoolean(ObservableBoolean observableBoolean) {
        this.observableBoolean = observableBoolean;
    }

    public ObservableField<String> getDepartmentError() {
        return departmentError;
    }

    public void setDepartmentError(ObservableField<String> departmentError) {
        this.departmentError = departmentError;
    }

    public ObservableField<String> getEditDepartmentName() {
        return editDepartmentName;
    }

    public void setEditDepartmentName(ObservableField<String> editDepartmentName) {
        this.editDepartmentName = editDepartmentName;
    }

    public ObservableField<Boolean> getDepartmentEditTextVisibility() {
        return departmentEditTextVisibility;
    }

    public void setDepartmentEditTextVisibility(ObservableField<Boolean> departmentEditTextVisibility) {
        this.departmentEditTextVisibility = departmentEditTextVisibility;
    }

    public ObservableField<String> getSelectedDepartmentName() {
        departmentName = selectedDepartmentName.get();
        departmentID = departmentHashMap.get(departmentName);
        departmentEditTextVisibility.set(false);
        selectedGroupName.set("");
        getGroupList();
        return selectedDepartmentName;

    }


    public void setSelectedDepartmentName(ObservableField<String> selectedDepartmentName) {
        this.selectedDepartmentName = selectedDepartmentName;

    }



    public MutableLiveData<ArrayAdapter<String>> getDepartmentAdapter() {
        return departmentAdapterMutableLiveData;
    }


    public MutableLiveData<String> getServerErrorMutableLiveData() {
        return serverErrorMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgressMutableLiveData() {
        return progressMutableLiveData;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }


    public MutableLiveData<ArrayAdapter<String>> getGroupAdapterMutableLiveData() {
        return groupAdapterMutableLiveData;
    }

    private void setUpDepartmentSpinner(List<Department> list) {

        List<String> departmentNameList = new ArrayList<>();
        for (Department model : list) {
            departmentNameList.add(model.getDepartmentName());
            departmentHashMap.put(model.getDepartmentName(), model.getDepartmentCode());
        }

        departmentAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, departmentNameList);

        departmentAdapterMutableLiveData.setValue(departmentAdapter);
    }

    private void setupGroupSpinner(List<Group> groupList) {

        List<String> groupNameList = new ArrayList<>();
        for (Group model : groupList) {
            groupNameList.add(model.getGroupName());
            groupHashMap.put(model.getGroupName(), model.getGroupCode());
        }

        groupAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, groupNameList);

        groupAdapterMutableLiveData.setValue(groupAdapter);
    }

    private void setupSubGroupSpinner(List<SubGroup> groupList) {

        List<String> subGroupNameList = new ArrayList<>();
        for (SubGroup model : subGroupList) {
            subGroupNameList.add(model.getSubGroupName());
            subGroupHashMap.put(model.getSubGroupName(), model.getSubGroupCode());
        }

        subGroupAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subGroupNameList);

        subGroupAdapterMutableLiveData.setValue(subGroupAdapter);
    }


    public void onClick(int key) {
        if (key == CONSTANTS.ADD_DEPARTMENT_BTN) {
            departmentEditTextVisibility.set(true);
            editDepartmentName.set("");
            departmentName = "";
            departmentID = "";
            departmentAction = "INSERT";
        } else if (key == CONSTANTS.EDIT_DEPARTMENT_BTN) {
            departmentEditTextVisibility.set(true);
            editDepartmentName.set(departmentName);
            departmentAction = "UPDATE";
        } else if (key == CONSTANTS.SAVE_DEPARTMENT_BTN) {
            String name = editDepartmentName.get();
            if (name != null && !name.isEmpty()) {
                departmentEditTextVisibility.set(false);
                saveDepartment();
                editDepartmentName.set("");
                if (departmentAction.equals("UPDATE")) {
                    departmentHashMap.remove(departmentName);
                }

            } else {
                toastMessage.setValue("Please enter Department Name");
                departmentError.set("Please enter Department Name\"");
                observableBoolean.set(true);
            }


        }
        else if (key == ADD_GROUP_BTN) {
            if (!departmentID.isEmpty())
            {
                groupEditTextVisibility.set(true);
                editGroupName.set("");
                groupName = "";
                groupID = "";
                groupAction = "INSERT";
            }
            else
            {
                toastMessage.setValue("Please select Department First");
            }

        }
        else if (key == CONSTANTS.EDIT_GROUP_BTN) {
            if (departmentID!=null&&!departmentID.isEmpty())
            {
                groupEditTextVisibility.set(true);
                editGroupName.set(groupName);
                groupAction = "UPDATE";
            }
            else
            {
                toastMessage.setValue("Please select Department First");
            }

        } else if (key == CONSTANTS.SAVE_GROUP_BTN) {
            if (departmentID!=null&&!departmentID.isEmpty())
            {
                String name = editGroupName.get();
                if (name != null && !name.isEmpty()) {
                    groupEditTextVisibility.set(false);
                    saveGroup();
                    editGroupName.set("");
                    if (groupAction.equals("UPDATE")) {
                        groupHashMap.remove(groupName);
                    }

                } else {
                    toastMessage.setValue("Please enter Group Name");
                    departmentError.set("Please enter Group Name\"");
                    observableBoolean.set(true);
                }
            }
            else
            {
                toastMessage.setValue("Please select Department First");
            }



        }   else if (key == ADD_SUB_GROUP_BTN) {
            if (!departmentID.isEmpty()&& !groupID.isEmpty())
            {
                subGroupEditTextVisibility.set(true);
                editSubGroupName.set("");
                subGroupName = "";
                subGroupID = "";
                subGroupAction = "INSERT";
            }
            else
            {
                toastMessage.setValue("Please select Group First");
            }

        }
        else if (key == CONSTANTS.EDIT_SUB_GROUP_BTN) {
            if (departmentID!=null&&!departmentID.isEmpty()&&
                    groupID!=null&&!groupID.isEmpty())
            {
                subGroupEditTextVisibility.set(true);
                editSubGroupName.set(subGroupName);
                subGroupAction = "UPDATE";
            }
            else
            {
                toastMessage.setValue("Please select Group First");
            }

        } else if (key == CONSTANTS.SAVE_SUB_GROUP_BTN) {
            if (departmentID!=null&&!departmentID.isEmpty()&&
                    groupID!=null&&!groupID.isEmpty())
            {
                String name = editSubGroupName.get();
                if (name != null && !name.isEmpty()) {
                    subGroupEditTextVisibility.set(false);
                    saveSubGroup();
                    editSubGroupName.set("");
                    if (subGroupAction.equals("UPDATE")) {
                        subGroupHashMap.remove(subGroupName);
                    }

                } else {
                    toastMessage.setValue("Please enter Sub Group Name");
                    departmentError.set("Please enter Sub Group Name\"");
                    observableBoolean.set(true);
                }
            }
            else
            {
                toastMessage.setValue("Please select Group First");
            }



        }

    }

    private void getSubGroupList() {
        if (groupID != null&&departmentID!=null) {

            String businessId = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

            DepartmentRepository.getInstance().getSubGroupFromServer(businessId, departmentID,groupID);

            progressMutableLiveData.setValue(true);
            getServerResponse();
        }
    }

    private void getGroupList() {
        if (departmentID != null) {

            String businessId = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

            DepartmentRepository.getInstance().getGroupFromServer(businessId, departmentID);

            progressMutableLiveData.setValue(true);
        }
    }

    public void getDepartmentList() {
        String businessId = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        getServerResponse();

        DepartmentRepository.getInstance().getDepartmentFromServer(businessId);
        progressMutableLiveData.setValue(true);

    }

    private void saveGroup() {
        if (!groupAction.isEmpty()) {
            String userID = SharedPreferenceHelper.getInstance(getApplication()).getUserID();
            String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
            Group group= new Group();
            group.setGroupCode(groupID);
            group.setGroupName(editGroupName.get().toUpperCase(Locale.ROOT));
            group.setBusinessID(businessID);
            group.setDepartmentCode(departmentID);
            group.setUserID(userID);
            group.setAction(groupAction);

            DepartmentRepository.getInstance().saveGroup(group);
            getServerResponse();
            progressMutableLiveData.setValue(false);



        } else {
            toastMessage.setValue("Please Select Action\n Edit or Add");
        }
    }

    private void saveSubGroup() {
        if (!subGroupAction.isEmpty()) {
            String userID = SharedPreferenceHelper.getInstance(getApplication()).getUserID();
            String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
            SubGroup subGroup= new SubGroup();
            subGroup.setGroupCode(groupID);
            subGroup.setSubGroupName(editSubGroupName.get().toUpperCase(Locale.ROOT));
            subGroup.setBusinessId(businessID);
            subGroup.setDepartmentCode(departmentID);
            subGroup.setUserId(userID);
            subGroup.setAction(subGroupAction);
            subGroup.setSubGroupCode(subGroupID);

            DepartmentRepository.getInstance().saveSubGroup(subGroup);
            getServerResponse();
            progressMutableLiveData.setValue(false);



        } else {
            toastMessage.setValue("Please Select Action\n Edit or Add");
        }
    }

    private void saveDepartment() {
        if (!departmentAction.isEmpty()) {
            String userID = SharedPreferenceHelper.getInstance(getApplication()).getUserID();
            String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
            Department department = new Department();
            department.setDepartmentCode(departmentID);
            department.setDepartmentName(editDepartmentName.get().toUpperCase(Locale.ROOT));
            department.setBusinessID(businessID);
            department.setUserID(userID);
            department.setAction(departmentAction);
            DepartmentRepository.getInstance().saveDepartment(department);
            progressMutableLiveData.setValue(true);

        } else {
            toastMessage.setValue("Please Select Action\n Edit or Add");
        }


    }


    private void getServerResponse() {

        DepartmentRepository.getInstance().setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object != null) {
                    if (key == GET_DEPARTMENT) {

                        GetDepartmentResponse getDepartmentResponse = (GetDepartmentResponse) object;
                        departmentList = getDepartmentResponse.getDepartmentList();

                        setUpDepartmentSpinner(departmentList);
                        progressMutableLiveData.setValue(false);

                    } else if (key == GET_GROUP) {

                        GetGroupResponse getGroupResponse = (GetGroupResponse) object;
                        groupList = getGroupResponse.getGroupList();

                        setupGroupSpinner(groupList);
                        progressMutableLiveData.setValue(false);

                    } else if (key == GET_SUB_GROUP) {

                        GetSubGroup getGroupResponse = (GetSubGroup) object;
                        subGroupList = getGroupResponse.getSubGroupList();

                        setupSubGroupSpinner(subGroupList);
                        progressMutableLiveData.setValue(false);

                    } else if (key == SERVER_ERROR) {
                        String error = (String) object;

                        serverErrorMutableLiveData.setValue(error);
                        progressMutableLiveData.setValue(false);
                        departmentAction = "";

                    } else if (key == SERVER_RESPONSE_DEPARTMENT) {

                        SaveDepartmentResponse departmentResponse = (SaveDepartmentResponse) object;
                        Department department = departmentResponse.getDepartment();
                        removeDepartment(departmentID);
                        departmentList.add(department);
                        departmentID = department.getDepartmentCode();
                        departmentName = department.getDepartmentName();
                        selectedDepartmentName.set(departmentName);
                        departmentHashMap.put(department.getDepartmentName(), department.getDepartmentCode());
                        setUpDepartmentSpinner(departmentList);
                        getGroupList();
                        progressMutableLiveData.setValue(false);
                        toastMessage.setValue(departmentResponse.getMessage());


                    }else if (key == SERVER_RESPONSE_GROUP) {

                        SaveGroupResponse saveGroupResponse = (SaveGroupResponse) object;
                        Group group = saveGroupResponse.getGroup();
                        removeGroup(groupID);
                        groupList.add(group);
                        groupID = group.getGroupCode();
                        groupName = group.getGroupName();
                        selectedGroupName.set(groupName);
                        groupHashMap.put(group.getGroupName(), group.getGroupCode());
                        setupGroupSpinner(groupList);
                        progressMutableLiveData.setValue(false);
                        toastMessage.setValue(saveGroupResponse.getMessage());

                        getSubGroupList();

                    }else if (key == SERVER_RESPONSE_SUB_GROUP) {

                        SaveSubGroupResponse saveSubGroupResponse = (SaveSubGroupResponse) object;
                        SubGroup subGroup = saveSubGroupResponse.getSubGroup();
                        removeSubGroup(subGroupID);
                        subGroupList.add(subGroup);
                        subGroupID = subGroup.getSubGroupCode();
                        subGroupName = subGroup.getSubGroupName();
                        selectedSubGroupName.set(subGroupName);
                        subGroupHashMap.put(subGroup.getSubGroupName(), subGroup.getSubGroupCode());
                        setupSubGroupSpinner(subGroupList);
                        progressMutableLiveData.setValue(false);
                        toastMessage.setValue(saveSubGroupResponse.getMessage());


                    }
                }
            }
        });
    }

    private void removeSubGroup(String subGroupID) {

        for (SubGroup subGroup:subGroupList)
        {
            if (subGroup.getSubGroupCode().equals(subGroupID))
            {
                subGroupList.remove(subGroup);
                return;
            }
        }
    }

    private void removeGroup(String groupID) {

        for (Group group:groupList)
        {
            if (group.getGroupCode().equals(groupID))
            {
                groupList.remove(group);
                return;
            }
        }
    }

    private void removeDepartment(String departmentID) {

        for (Department department:departmentList)
        {
            if (department.getDepartmentCode().equals(departmentID))
            {
                departmentList.remove(department);
                return;
            }
        }
    }


}
