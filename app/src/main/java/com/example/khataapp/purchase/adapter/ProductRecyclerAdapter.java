package com.example.khataapp.purchase.adapter;

import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.ItemCardBinding;
import com.example.khataapp.databinding.ItemCardTwoBinding;
import com.example.khataapp.models.Item;
import com.example.khataapp.purchase.viewmodel.PurchaseViewModel;
import com.example.khataapp.stock.adapter.ItemListAdapter;
import com.example.khataapp.stock.viewmodel.StockViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    private final List<Item> itemList ;
    private LayoutInflater layoutInflater;
    private PurchaseViewModel viewModel;

    public ProductRecyclerAdapter(PurchaseViewModel viewModel)
    {
        itemList = new ArrayList<>();
        this.viewModel= viewModel;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCardTwoBinding binding = ItemCardTwoBinding.inflate(layoutInflater,parent,false);


        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Item item = itemList.get(position);

        holder.mBinding.setViewModel(viewModel);
        holder.mBinding.setItem(item);
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

    public void addItem(Item item)
    {
        if (!itemList.contains(item))
        {
            itemList.add(item);
            notifyDataSetChanged();
        }
    }

    public void removeItem(Item item)
    {
        if (item!=null)
        {
            double qty= Item.totalQty-item.getQty();
            double amount = Item.totalAmount-item.getAmount();
            Item.setTotalAmount(amount);
            Item.setTotalQty(qty);
            item.setQty(0);
            item.setAmount(0);
            viewModel.getTotalAmount().set(String.valueOf(Item.totalAmount));
            viewModel.getTotalQty().set(String.valueOf(Item.totalQty));
            itemList.remove(item);
            notifyDataSetChanged();
        }
    }

    public List<Item> getItemList() {
        return itemList;
    }


    public boolean checkItemExists(Item item)
    {
        return itemList.contains(item);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        ItemCardTwoBinding mBinding;
        public ProductViewHolder(@NonNull ItemCardTwoBinding binding) {
            super(binding.getRoot());

            mBinding= binding;

            mBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mBinding.etCost.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    mBinding.etFreeQty.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    mBinding.etQty.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    mBinding.etCost.setFocusableInTouchMode(true);
                    mBinding.etQty.setFocusableInTouchMode(true);
                    mBinding.etFreeQty.setFocusableInTouchMode(true);
                    mBinding.itemCardTwoCard.setCardBackgroundColor(Color.parseColor("#F2F2F2"));
                    mBinding.btnDone.setVisibility(View.VISIBLE);
                    mBinding.btnEdit.setVisibility(View.GONE);

                }
            });
            mBinding.btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mBinding.etCost.setInputType(InputType.TYPE_NULL);
                    mBinding.etFreeQty.setInputType(InputType.TYPE_NULL);
                    mBinding.etQty.setInputType(InputType.TYPE_NULL);
                    mBinding.etCost.setFocusableInTouchMode(false);
                    mBinding.etQty.setFocusableInTouchMode(false);
                    mBinding.etFreeQty.setFocusableInTouchMode(false);
                    mBinding.itemCardTwoCard.setCardBackgroundColor(Color.WHITE);
                    mBinding.btnEdit.setVisibility(View.VISIBLE);
                    mBinding.btnDone.setVisibility(View.GONE);

                }
            });
        }
    }
}
