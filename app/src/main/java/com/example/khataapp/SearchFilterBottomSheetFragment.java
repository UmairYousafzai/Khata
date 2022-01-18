package com.example.khataapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.khataapp.databinding.SearchFilterBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class SearchFilterBottomSheetFragment extends BottomSheetDialogFragment {


    private SearchFilterBottomSheetBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mBinding = SearchFilterBottomSheetBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

}