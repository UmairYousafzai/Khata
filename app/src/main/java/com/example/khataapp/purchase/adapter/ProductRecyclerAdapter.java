package com.example.khataapp.purchase.adapter;

import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.ItemCardBinding;
import com.example.khataapp.databinding.ItemCardTwoBinding;
import com.example.khataapp.models.Item;
import com.example.khataapp.purchase.viewmodel.PurchaseViewModel;

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

            item.setQty(0);
            item.setAmount(0);
            viewModel.getSubTotalAmount().set(String.valueOf(Item.totalAmount));
            viewModel.getTotalQty().set(String.valueOf(Item.totalQty));
            try
            {
                if (viewModel.getGstFlag().get())
                {
                    double stAmount= Item.totalAmount;
                    double gstPercentage= (17*stAmount)/100;
                    double totalAmount= stAmount+gstPercentage;
                    viewModel.getTotalAmount().set(String.valueOf(totalAmount));
                    viewModel.getGstTax().set(String.valueOf(gstPercentage));

                }
                else
                {
                    viewModel.getTotalAmount().set(String.valueOf(Item.totalAmount));
                }
            }
            catch (Exception e)
            {
                Log.e(ProductRecyclerAdapter.class.getSimpleName(),e.toString());
            }


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

    public  class ProductViewHolder extends RecyclerView.ViewHolder
    {
        ItemCardTwoBinding mBinding;
        public ProductViewHolder(@NonNull ItemCardTwoBinding binding) {
            super(binding.getRoot());

            mBinding= binding;

            mBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mBinding.etCost.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    mBinding.freeQty.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    mBinding.qty.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    mBinding.etCost.setFocusableInTouchMode(true);
                    mBinding.qty.setFocusableInTouchMode(true);
                    mBinding.freeQty.setFocusableInTouchMode(true);
                    mBinding.itemCardTwoCard.setCardBackgroundColor(Color.parseColor("#F2F2F2"));
                    mBinding.btnDone.setVisibility(View.VISIBLE);
                    mBinding.btnEdit.setVisibility(View.GONE);
                    viewModel.getToastMessage().setValue("Edit Mode Enable");


                }
            });
            mBinding.btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mBinding.etCost.setInputType(InputType.TYPE_NULL);
                    mBinding.freeQty.setInputType(InputType.TYPE_NULL);
                    mBinding.qty.setInputType(InputType.TYPE_NULL);
                    mBinding.etCost.setFocusableInTouchMode(false);
                    mBinding.qty.setFocusableInTouchMode(false);
                    mBinding.freeQty.setFocusableInTouchMode(false);
                    mBinding.itemCardTwoCard.setCardBackgroundColor(Color.WHITE);
                    mBinding.btnEdit.setVisibility(View.VISIBLE);
                    mBinding.btnDone.setVisibility(View.GONE);
                    mBinding.setItem(itemList.get(getAdapterPosition()));

                    viewModel.getToastMessage().setValue("Item Edit Successfully");
                    viewModel.getSubTotalAmount().set(String.valueOf(Item.totalAmount));
                    viewModel.getTotalQty().set(String.valueOf(Item.totalQty));
                    try
                    {
                        if (viewModel.getGstFlag().get())
                        {
                            double gstPercentage= (17*Item.totalAmount)/100;
                            viewModel.getGstTax().set(String.valueOf(gstPercentage));
                            viewModel.getTotalAmount().set(String.valueOf(Item.totalAmount+gstPercentage));

                        }
                        else
                        {
                            viewModel.getTotalAmount().set(String.valueOf(Item.totalAmount));
                        }
                    }
                    catch (Exception e)
                    {
                        Log.e(ProductRecyclerAdapter.class.getSimpleName(),e.toString());
                    }

                }
            });
        }
    }
}
