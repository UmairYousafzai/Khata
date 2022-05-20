package com.example.khataapp.views.stockAdjustment.adapter;

import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.StockAdjustItemCardBinding;
import com.example.khataapp.models.Item;
import com.example.khataapp.views.stockAdjustment.viewModel.StockAdjustViewModel;

import java.util.ArrayList;
import java.util.List;

public class StockAdjustProductAdapter extends RecyclerView.Adapter<StockAdjustProductAdapter.ProductViewHolder> {

    private final List<Item> itemList;
    private LayoutInflater layoutInflater;
    private final Item beforeEditItem;
    private StockAdjustViewModel viewModel;
    private boolean isAuthenticate = false;

    public StockAdjustProductAdapter(StockAdjustViewModel viewModel) {
        this.viewModel = viewModel;
        itemList = new ArrayList<>();
        beforeEditItem = new Item();
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        StockAdjustItemCardBinding binding = StockAdjustItemCardBinding.inflate(layoutInflater, parent, false);


        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Item item = itemList.get(position);


        if (isAuthenticate) {
            disableUi(holder.mBinding);

        }
        holder.mBinding.setItem(item);
        holder.mBinding.executePendingBindings();


    }

    private void disableUi(StockAdjustItemCardBinding mBinding) {
        mBinding.btnEdit.setEnabled(false);
        mBinding.btnCancel.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public void setItemList(List<Item> list) {
        itemList.clear();
        if (list != null) {
            itemList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        if (!itemList.contains(item)) {
            itemList.add(item);
            notifyDataSetChanged();
        }
    }

    public void removeItem(Item item) {
        if (item != null) {
            if (editCalculation(item)) {
                itemList.remove(item);

            }
            notifyDataSetChanged();
        }
    }

    public void setAuthenticate(boolean authenticate) {
        isAuthenticate = authenticate;
    }

    private boolean editCalculation(Item item) {
        double qty = 0, amount = 0;
        boolean flag = false;


        qty = Double.parseDouble(viewModel.getTotalQty().get()) - item.getQty();
        amount = Double.parseDouble(viewModel.getSubTotalAmount().get()) - item.getAmount();
        viewModel.getSubTotalAmount().set(String.valueOf(amount));
        viewModel.getTotalQty().set(String.valueOf(qty));
        try {
            if (viewModel.getGstFlag().get()) {
                double stAmount = Double.parseDouble(viewModel.getSubTotalAmount().get());
                double gstPercentage = (17 * stAmount) / 100;
                double totalAmount = stAmount + gstPercentage;
                viewModel.getTotalAmount().set(String.valueOf(totalAmount));
                viewModel.getGstTax().set(String.valueOf(gstPercentage));

            } else {
                viewModel.getTotalAmount().set(String.valueOf(amount));
            }
            flag = true;
        } catch (Exception e) {
            Log.e(StockAdjustProductAdapter.class.getSimpleName(), e.toString());
            flag = false;

        }


        return flag;
    }

    public List<Item> getItemList() {
        List<Item> list = new ArrayList<>();
        for (Item model : itemList) {
            model.setProductImage("");
            list.add(model);
        }
        return list;
    }

    public void clearList() {
        itemList.clear();
        notifyDataSetChanged();
    }


    public boolean checkItemExists(Item item) {
        return itemList.contains(item);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        StockAdjustItemCardBinding mBinding;

        public ProductViewHolder(@NonNull StockAdjustItemCardBinding binding) {
            super(binding.getRoot());

            mBinding = binding;

            mBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    beforeEditItem.setUnitCost(itemList.get(getAdapterPosition()).getUnitCost());
                    beforeEditItem.setQty(itemList.get(getAdapterPosition()).getQty());
                    mBinding.etCost.setInputType(InputType.TYPE_CLASS_NUMBER);
                    mBinding.freeQty.setInputType(InputType.TYPE_CLASS_NUMBER);
                    mBinding.qty.setInputType(InputType.TYPE_CLASS_NUMBER);
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
                    editCalculation(beforeEditItem);
                    viewModel.addItemToProductAdapter(itemList.get(getAdapterPosition()));
                    viewModel.getToastMessage().setValue("Item Edit Successfully");


                }
            });
        }


    }
}
