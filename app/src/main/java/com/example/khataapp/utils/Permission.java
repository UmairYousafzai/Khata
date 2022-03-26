package com.example.khataapp.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.khataapp.databinding.CustomAlertDialogBinding;

public class Permission {

    private Context mContext;
    private Activity mActivity;
    private AlertDialog alertDialog;

    public Permission(Context mContext, Activity mActivity) {
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void getCameraPermission() {

        String[] permissionArray = new String[]{Manifest.permission.CAMERA};
        CustomAlertDialogBinding dialogBinding = CustomAlertDialogBinding.inflate(mActivity.getLayoutInflater());
        AlertDialog alertDialog = new AlertDialog.Builder( mContext).setView(dialogBinding.getRoot()).setCancelable(true).create();


        dialogBinding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(mActivity,
                        permissionArray,
                        CONSTANTS.PERMISSION_REQUEST_CODE);
            }
        });



        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)) {

            dialogBinding.title.setText("Storage Permission Needed");
            dialogBinding.body.setText("This Permission Needed For The Better Experience Of The App. ");
            alertDialog.show();

        } else {
            ActivityCompat.requestPermissions(mActivity, permissionArray, CONSTANTS.PERMISSION_REQUEST_CODE);

        }


    }

    public void getStoragePermission() {

        String[] permissionArray = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        CustomAlertDialogBinding dialogBinding = CustomAlertDialogBinding.inflate(mActivity.getLayoutInflater());
        AlertDialog alertDialog = new AlertDialog.Builder( mContext).setView(dialogBinding.getRoot()).setCancelable(true).create();


        dialogBinding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(mActivity,
                        permissionArray,
                        CONSTANTS.PERMISSION_REQUEST_CODE);
            }
        });



        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            dialogBinding.title.setText("Storage Permission Needed");
            dialogBinding.body.setText("This Permission Needed For The Better Experience Of The App. ");
            alertDialog.show();


        } else {
            ActivityCompat.requestPermissions(mActivity, permissionArray, CONSTANTS.PERMISSION_REQUEST_CODE);

        }


    }

}