package com.example.khataapp.stock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.ItemCardBinding;
import com.example.khataapp.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private List<Item> itemList ;
    private LayoutInflater layoutInflater;

    public ItemListAdapter()
    {
        itemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCardBinding binding = ItemCardBinding.inflate(layoutInflater,parent,false);


        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Item item = itemList.get(position);

        holder.mBinding.setItem(item);
        holder.mBinding.executePendingBindings();


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ItemCardBinding mBinding;
        public ItemViewHolder(@NonNull ItemCardBinding binding) {
            super(binding.getRoot());

            mBinding= binding;
        }
    }
}
