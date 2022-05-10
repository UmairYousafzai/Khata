package com.example.khataapp.views.department;

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

        getLiveData();
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());

    }

    private void getLiveData() {
        progressDialog.show();


        viewModel.getDepartmentList();

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