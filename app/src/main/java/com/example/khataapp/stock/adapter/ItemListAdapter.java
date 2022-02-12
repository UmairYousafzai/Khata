package com.example.khataapp.stock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.ItemCardBinding;
import com.example.khataapp.models.Item;
import com.example.khataapp.stock.viewmodel.StockViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private final List<Item> itemList ;
    private LayoutInflater layoutInflater;
    private StockViewModel viewModel;

    public ItemListAdapter(StockViewModel viewModel)
    {
        this.viewModel= viewModel;
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
        holder.mBinding.setViewmodel(viewModel);
        holder.mBinding.executePendingBindings();


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(List<Item> list)
    {
        itemList.clear();
        if (list!=null)
        {
            itemList.addAll(list);


        }
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ItemCardBinding mBinding;
        public ItemViewHolder(@NonNull ItemCardBinding binding) {
            super(binding.getRoot());

            mBinding= binding;
        }
    }
}
