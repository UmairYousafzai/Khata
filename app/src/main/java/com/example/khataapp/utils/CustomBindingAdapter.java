package com.example.khataapp.utils;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomBindingAdapter {

    @BindingAdapter("setAdapter")
    public static void setAdapter(RecyclerView recyclerView,RecyclerView.Adapter<?> adapter)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("setTextAdapter")
    public static void setAdapter(AutoCompleteTextView textView, ArrayAdapter<String> adapter)
    {
        textView.setAdapter(adapter);
    }
}
