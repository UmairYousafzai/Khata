package com.example.khataapp.views.sale.adapter;

import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.SaleItemCardBinding;
import com.example.khataapp.models.Item;
import com.example.khataapp.views.purchase.adapter.ProductRecyclerAdapter;
import com.example.khataapp.views.sale.viewModel.SaleDocViewModel;
import com.example.khataapp.views.sale.viewModel.SaleReturnDocViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductSaleRecyclerAdapter extends RecyclerView.Adapter<ProductSaleRecyclerAdapter.SaleProductViewHolder> {

    private final List<Item> itemList;
    private LayoutInflater layoutInflater;
    private SaleReturnDocViewModel saleReturnDocViewModel;
    private SaleDocViewModel saleViewModel;
    private int key;
    private boolean isAuthenticate = false;
    private Item beforeEditItem;

    public ProductSaleRecyclerAdapter(SaleReturnDocViewModel saleReturnDocViewModel, SaleDocViewModel saleViewModel, int key) {
        itemList = new ArrayList<>();
        if (saleReturnDocViewModel != null)
            this.saleReturnDocViewModel = saleReturnDocViewModel;
        if (saleViewModel != null)
            this.saleViewModel = saleViewModel;
        this.key = key;
        beforeEditItem = new Item();
    }

    @NonNull
    @Override
    public SaleProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        SaleItemCardBinding binding = SaleItemCardBinding.inflate(layoutInflater, parent, false);


        return new SaleProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleProductViewHolder holder, int position) {

        Item item = itemList.get(position);

        if (key == 2) {
            holder.mBinding.setSaleReturnViewModel(saleReturnDocViewModel);

        } else {
            holder.mBinding.setSaleViewModel(saleViewModel);

        }
        if (isAuthenticate) {
            disableUi(holder.mBinding);

        }
        holder.mBinding.setKey(key);
        holder.mBinding.setItem(item);
        holder.mBinding.executePendingBindings();


    }

    private void disableUi(SaleItemCardBinding mBinding) {
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

    private boolean editCalculation(Item item) {
        double qty = 0, amount = 0;
        boolean flag = false;


        if (key == 1) {

            qty = Double.parseDouble(saleViewModel.getTotalQty().get()) - item.getQty();
            amount = Double.parseDouble(saleViewModel.getSubTotalAmount().get()) - item.getAmount();
            saleViewModel.getSubTotalAmount().set(String.valueOf(amount));
            saleViewModel.getTotalQty().set(String.valueOf(qty));
            try {
                if (saleViewModel.getGstFlag().get()) {
                    double stAmount = Double.parseDouble(saleViewModel.getSubTotalAmount().get());
                    double gstPercentage = (17 * stAmount) / 100;
                    double totalAmount = stAmount + gstPercentage;
                    saleViewModel.getTotalAmount().set(String.valueOf(totalAmount));
                    saleViewModel.getGstTax().set(String.valueOf(gstPercentage));

                } else {
                    saleViewModel.getTotalAmount().set(String.valueOf(amount));
                }
                flag = true;
            } catch (Exception e) {
                Log.e(ProductRecyclerAdapter.class.getSimpleName(), e.toString());
                flag = false;

            }

        } else {
            qty = Double.parseDouble(saleReturnDocViewModel.getTotalQty().get()) - item.getQty();
            amount = Double.parseDouble(saleReturnDocViewModel.getSubTotalAmount().get()) - item.getAmount();
            saleReturnDocViewModel.getSubTotalAmount().set(String.valueOf(amount));
            saleReturnDocViewModel.getTotalQty().set(String.valueOf(qty));
            try {
                if (saleReturnDocViewModel.getGstFlag().get()) {
                    double stAmount = Double.parseDouble(saleReturnDocViewModel.getSubTotalAmount().get());
                    double gstPercentage = (17 * stAmount) / 100;
                    double totalAmount = stAmount + gstPercentage;
                    saleReturnDocViewModel.getTotalAmount().set(String.valueOf(totalAmount));
                    saleReturnDocViewModel.getGstTax().set(String.valueOf(gstPercentage));

                } else {
                    saleReturnDocViewModel.getTotalAmount().set(String.valueOf(amount));
                }
                flag = true;

            } catch (Exception e) {
                flag = false;
                Log.e(ProductRecyclerAdapter.class.getSimpleName(), e.toString());
            }
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

    public void setAuthenticate(boolean authenticate) {
        isAuthenticate = authenticate;
    }

    public boolean checkItemExists(Item item) {
        return itemList.contains(item);
    }

    public class SaleProductViewHolder extends RecyclerView.ViewHolder {
        SaleItemCardBinding mBinding;

        public SaleProductViewHolder(@NonNull SaleItemCardBinding binding) {
            super(binding.getRoot());

            mBinding = binding;

            mBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    beforeEditItem.setUnitRetail(itemList.get(getAdapterPosition()).getUnitRetail());
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
                    if (key == 2) {
                        saleReturnDocViewModel.getToastMessage().setValue("Edit Mode Enable");

                    } else {
                        saleViewModel.getToastMessage().setValue("Edit Mode Enable");

                    }


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
                    if (key == 1) {
                        saleViewModel.addItemToProductAdapter(itemList.get(getAdapterPosition()));
                        saleViewModel.getToastMessage().setValue("Item Edit Successfully");

                    } else {
                        saleReturnDocViewModel.addItemToProductAdapter(itemList.get(getAdapterPosition()));
                        saleReturnDocViewModel.getToastMessage().setValue("Item Edit Successfully");
                    }
                }
            });
        }
    }
}
