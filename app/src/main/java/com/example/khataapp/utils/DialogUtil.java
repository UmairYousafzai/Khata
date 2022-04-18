package com.example.khataapp.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.khataapp.R;

public class DialogUtil {
    private static  DialogUtil instance;

    public static synchronized DialogUtil getInstance()
    {
        if (instance==null)
        {
            instance= new DialogUtil();
        }

        return instance;
    }

        public AlertDialog  getProgressDialog(Context context) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
            dialogBuilder.setView(dialogView);

            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.setCancelable(false);
            return alertDialog;

        }

}
