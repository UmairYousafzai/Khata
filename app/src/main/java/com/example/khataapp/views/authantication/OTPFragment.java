package com.example.khataapp.views.authantication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khataapp.databinding.FragmentOTPBinding;

public class OTPFragment extends Fragment {

    private FragmentOTPBinding mBinding;
    private NavController navController;
    private long phoneNumber;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentOTPBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        if (getArguments()!=null)
        {
            phoneNumber = OTPFragmentArgs.fromBundle(getArguments()).getPhoneNumber();
            String message= "OTP Send To "+ phoneNumber;
            mBinding.tvPhoneNumber.setText(message);
        }

        btnListener();
        setUpBottomNavigation();
    }

    private void setUpBottomNavigation() {


    }

    private void btnListener() {

        mBinding.tvChnageNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.popBackStack();

            }
        });

        mBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = OTPFragmentDirections.actionOTPFragmentToHomeFragment();

                navController.navigate(navDirections);
            }
        });
    }
}