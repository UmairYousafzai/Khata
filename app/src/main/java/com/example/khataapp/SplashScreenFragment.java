package com.example.khataapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khataapp.databinding.FragmentSplashScreenBinding;


public class SplashScreenFragment extends Fragment {

    private FragmentSplashScreenBinding mBinding;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentSplashScreenBinding.inflate(inflater,container,false);

        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                NavDirections navDirections = SplashScreenFragmentDirections.actionSplashScreenFragmentToSignUpFragment();

                navController.navigate(navDirections);


            }
        }, 1000);
    }
}