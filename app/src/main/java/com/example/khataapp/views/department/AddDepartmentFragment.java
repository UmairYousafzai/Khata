package com.example.khataapp.views.department;

import static com.example.khataapp.utils.CONSTANTS.ADD_DEPARTMENT_BTN;
import static com.example.khataapp.utils.CONSTANTS.ADD_GROUP_BTN;
import static com.example.khataapp.utils.CONSTANTS.ADD_SUB_GROUP_BTN;
import static com.example.khataapp.utils.CONSTANTS.EDIT_DEPARTMENT_BTN;
import static com.example.khataapp.utils.CONSTANTS.EDIT_GROUP_BTN;
import static com.example.khataapp.utils.CONSTANTS.EDIT_SUB_GROUP_BTN;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.khataapp.databinding.FragmentAddDepartmentBinding;
import com.example.khataapp.utils.DialogUtil;

public class AddDepartmentFragment extends Fragment {

    private FragmentAddDepartmentBinding mBinding;
    private DepartmentViewModel viewModel;
    private AlertDialog progressDialog ;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentAddDepartmentBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DepartmentViewModel.class);
        mBinding.setViewModel(viewModel);

        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());
        getLiveData();


    }

    private void getLiveData() {
        progressDialog.show();


        viewModel.getDepartmentList();

        viewModel.getClickActionMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer key) {

                if (key==ADD_DEPARTMENT_BTN||key== EDIT_DEPARTMENT_BTN)
                {
                    mBinding.etDepartment.requestFocus();
                    mBinding.etDepartment.setSelection(mBinding.etDepartment.getText().length());
                }
                else if (key==ADD_GROUP_BTN|| key==EDIT_GROUP_BTN)
                {
                    mBinding.etGroup.requestFocus();

                    mBinding.etGroup.setSelection(mBinding.etGroup.getText().length());

                } else if (key==ADD_SUB_GROUP_BTN|| key==EDIT_SUB_GROUP_BTN) {
                    mBinding.etSubGroup.requestFocus();
                    mBinding.etSubGroup.setSelection(mBinding.etSubGroup.getText().length());

                }
            }
        });

        viewModel.getProgressMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean check) {

                if (check)
                {
                    progressDialog.show();
                }else
                {
                    progressDialog.dismiss();
                }
            }
        });
        viewModel.getServerErrorMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s!=null)
                {

                    Toast.makeText(requireContext(), ""+s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getDepartmentAdapter().observe(getViewLifecycleOwner(), new Observer<ArrayAdapter<String>>() {
            @Override
            public void onChanged(ArrayAdapter<String> stringArrayAdapter) {

                if (stringArrayAdapter!=null)
                {
                    mBinding.departmentSpinner.setAdapter(stringArrayAdapter);
                }
            }
        });

        viewModel.getGroupAdapterMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayAdapter<String>>() {
            @Override
            public void onChanged(ArrayAdapter<String> stringArrayAdapter) {

                if (stringArrayAdapter!=null)
                {
                    mBinding.groupSpinner.setAdapter(stringArrayAdapter);
                }
            }
        });
        viewModel.getSubGroupAdapterMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayAdapter<String>>() {
            @Override
            public void onChanged(ArrayAdapter<String> stringArrayAdapter) {

                if (stringArrayAdapter!=null)
                {
                    mBinding.subGroupSpinner.setAdapter(stringArrayAdapter);
                }
            }
        });

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s!=null)
                {
                    Toast.makeText(requireContext(), ""+s, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}