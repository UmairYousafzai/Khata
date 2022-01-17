package com.example.khataapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.khataapp.databinding.FragmentHomeBinding;

import java.util.Calendar;

public class HomeFragment extends Fragment {
    private final int CUSTOMER_FRAMENT =1 ,SUPPLIER_FRAGMENT =2, ALL_FRAGMENT =3;


    private FragmentHomeBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mBinding = FragmentHomeBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        change_view(CUSTOMER_FRAMENT);
        MeowBottomNavigation navBar = getActivity().findViewById(R.id.bottom_view);
        navBar.setVisibility(View.VISIBLE);
        navBar.show(1,true);


        mBinding.btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_view(CUSTOMER_FRAMENT);
            }
        });
        mBinding.btnSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_view(SUPPLIER_FRAGMENT);
            }
        });
        mBinding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_view(ALL_FRAGMENT);
            }
        });
        mBinding.ImageButtonCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate();
            }
        });


    }
    private void getDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                StringBuilder builder = new StringBuilder();
                builder.append(i).append("/").append(i1 + 1).append("/").append(i2);
               //getDatefrombuilder
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void change_view(int i) {
        if(i== CUSTOMER_FRAMENT){
            mBinding.viewCustomer.setVisibility(View.VISIBLE);
            mBinding.viewSupplierIndicator.setVisibility(View.INVISIBLE);
            mBinding.viewAllIndicator.setVisibility(View.INVISIBLE);
        }else if(i== SUPPLIER_FRAGMENT){
            mBinding.viewCustomer.setVisibility(View.INVISIBLE);
            mBinding.viewSupplierIndicator.setVisibility(View.VISIBLE);
            mBinding.viewAllIndicator.setVisibility(View.INVISIBLE);
        }else if(i== ALL_FRAGMENT){
            mBinding.viewCustomer.setVisibility(View.INVISIBLE);
            mBinding.viewSupplierIndicator.setVisibility(View.INVISIBLE);
            mBinding.viewAllIndicator.setVisibility(View.VISIBLE);
        }
    }
}