package com.example.khataapp.views.authantication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khataapp.databinding.FragmentPhoneNumberBinding;
import com.google.android.material.snackbar.Snackbar;
import com.hbb20.CountryCodePicker;

public class PhoneNumberFragment extends Fragment {

    private FragmentPhoneNumberBinding mBinding;
    private NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentPhoneNumberBinding.inflate(inflater,container,false);

        return  mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        checkPhoneValidation();
        btnListener();

    }

    private void btnListener() {

        mBinding.btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.ccp.isValidFullNumber())
                {
                    long phoneNumber= Long.parseLong(mBinding.etPhoneNumber.getText().toString().replaceAll(" ",""));

                    PhoneNumberFragmentDirections.ActionPhoneNumberFragmentToOTPFragment action = PhoneNumberFragmentDirections.actionPhoneNumberFragmentToOTPFragment();
                    action.setPhoneNumber(phoneNumber);

                    navController.navigate(action);


                }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(v, "Please Valid Phone Number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


            }
        });
    }

    private void checkPhoneValidation() {

        mBinding.ccp.registerCarrierNumberEditText(mBinding.etPhoneNumber);

        mBinding.ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {

                if (!isValidNumber)
                {
                    mBinding.etPhoneNumber.setError("Please enter valid number");
                }
            }
        });

    }
}