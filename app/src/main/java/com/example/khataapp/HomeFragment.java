package com.example.khataapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.khataapp.databinding.FragmentHomeBinding;
import com.example.khataapp.utils.CONSTANTS;

import java.util.Calendar;

public class HomeFragment extends Fragment {
    private final int CUSTOMER_FRAGMENT = 1, SUPPLIER_FRAGMENT = 2, ALL_FRAGMENT = 3;
    private FragmentHomeBinding mBinding;
    private NavController navController;
    private String businessName;
    private NavBackStackEntry navBackStackEntry;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        change_view(CUSTOMER_FRAGMENT);
        MeowBottomNavigation navBar = requireActivity().findViewById(R.id.bottom_view);
        navBar.setVisibility(View.VISIBLE);
        navBar.show(1, true);

        navController = NavHostFragment.findNavController(this);
        navBackStackEntry = navController.getBackStackEntry(R.id.homeFragment);
        final LifecycleEventObserver observer = (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_RESUME) && navBackStackEntry.getSavedStateHandle().contains(CONSTANTS.BUSINESS_NAME)) {

                businessName = navBackStackEntry.getSavedStateHandle().get(CONSTANTS.BUSINESS_NAME);
                mBinding.etBusinessName.setText(businessName);

            }
        };
        navBackStackEntry.getLifecycle().addObserver(observer);

        // As addObserver() does not automatically remove the observer, we
        // call removeObserver() manually when the view lifecycle is destroyed
        getViewLifecycleOwner().getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                    navBackStackEntry.getLifecycle().removeObserver(observer);

                }
            }
        });

        getDataFromDialog();
        getBusinessName();
        btnListener();


    }

    private void getDataFromDialog() {


        // Create our observer and add it to the NavBackStackEntry's lifecycle

    }

    private void getBusinessName() {

        if (getArguments() != null) {
            businessName = HomeFragmentArgs.fromBundle(getArguments()).getBusinessName();
            mBinding.etBusinessName.setText(businessName);
        }
    }

    private void btnListener() {
        mBinding.btnSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToSearchFilterBottomSheetFragment();
                navController.navigate(navDirections);
            }
        });
        mBinding.ImageButtonCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToCalendarFragment();
                navController.navigate(navDirections);
            }
        });

        mBinding.etBusinessName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToEditBusinessNameBottomSheetFragment();
                navController.navigate(navDirections);
            }
        });

        mBinding.btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_view(CUSTOMER_FRAGMENT);
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

    }


    private void change_view(int i) {
        if (i == CUSTOMER_FRAGMENT) {
            mBinding.viewCustomer.setVisibility(View.VISIBLE);
            mBinding.viewSupplierIndicator.setVisibility(View.GONE);
            mBinding.viewAllIndicator.setVisibility(View.GONE);
            mBinding.customerCard.setVisibility(View.VISIBLE);
            mBinding.supplierCard.setVisibility(View.GONE);
            mBinding.cardViewMakeInvoice.setVisibility(View.GONE);

        } else if (i == SUPPLIER_FRAGMENT) {
            mBinding.viewCustomer.setVisibility(View.GONE);
            mBinding.viewSupplierIndicator.setVisibility(View.VISIBLE);
            mBinding.viewAllIndicator.setVisibility(View.GONE);
            mBinding.customerCard.setVisibility(View.GONE);
            mBinding.supplierCard.setVisibility(View.VISIBLE);
            mBinding.cardViewMakeInvoice.setVisibility(View.GONE);

        } else if (i == ALL_FRAGMENT) {
            mBinding.viewCustomer.setVisibility(View.GONE);
            mBinding.viewSupplierIndicator.setVisibility(View.GONE);
            mBinding.viewAllIndicator.setVisibility(View.VISIBLE);
            mBinding.customerCard.setVisibility(View.VISIBLE);
            mBinding.supplierCard.setVisibility(View.GONE);
            mBinding.cardViewMakeInvoice.setVisibility(View.VISIBLE);
        }
    }
}