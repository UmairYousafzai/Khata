package com.example.khataapp.department;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.khataapp.R;
import com.example.khataapp.databinding.FragmentAddDepartmentBinding;

public class AddDepartmentFragment extends Fragment {

    private FragmentAddDepartmentBinding mBinding;
    private DepartmentViewModel viewModel;

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
    }

    private void getLiveData() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Department");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        viewModel.getDepartment();

        viewModel.getProgressMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer check) {

                if (check==1)
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

    }
}