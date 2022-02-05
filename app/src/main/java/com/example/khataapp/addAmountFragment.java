package com.example.khataapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khataapp.databinding.FragmentAddAmountBinding;

public class addAmountFragment extends Fragment {

    private FragmentAddAmountBinding mBinding;
    private double numberOne, numberTwo;
    private boolean isNumberOne = true, isNumberTwo = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAddAmountBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnListener();

    }

    private void btnListener() {

        mBinding.keyboardLayout.btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btnModulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });

                mBinding.keyboardLayout.btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mBinding.etAmount.setText("0");
            }
        });


    }


}