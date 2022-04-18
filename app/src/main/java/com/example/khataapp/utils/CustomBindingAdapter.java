package com.example.khataapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.khataapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

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

    @BindingAdapter("imageUrl")
    public static void loadImage(CircleImageView view, String url) {


            Bitmap bmp = null;
            try {
                byte[] data = Base64.decode(url, Base64.DEFAULT);

                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
            catch (Exception e)
            {
                Log.e(CustomBindingAdapter.class.getSimpleName(),e.toString());
            }


        Glide.with(view.getContext())
                .load(bmp)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(view);
    }
}
