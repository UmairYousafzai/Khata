package com.example.khataapp.views;

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

import com.example.khataapp.R;
import com.example.khataapp.databinding.FragmentMoneyBinding;
import com.example.khataapp.utils.CONSTANTS;
import com.example.khataapp.utils.SharedPreferenceHelper;

public class MoneyFragment extends Fragment {


    private FragmentMoneyBinding mBinding;
    private NavController navController;
    private NavBackStackEntry navBackStackEntry;
    private String businessName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentMoneyBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        navBackStackEntry = navController.getBackStackEntry(R.id.moneyFragment);

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
        setBusinessName();
        btnListener();
        getDataFromDialog();
    }

    private void setBusinessName() {
        String businessName=SharedPreferenceHelper.getInstance(requireContext()).getBusinessName();

        mBinding.etBusinessName.setText(businessName);

    }

    private void getDataFromDialog() {



    }

    private void btnListener() {

        mBinding.etBusinessName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = MoneyFragmentDirections.actionMoneyFragmentToEditBusinessNameBottomSheetFragment2();
                navController.navigate(navDirections);
            }
        });
        mBinding.ImageButtonCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = MoneyFragmentDirections.actionMoneyFragmentToCalendarFragment();
                navController.navigate(navDirections);
            }
        });
    }
}