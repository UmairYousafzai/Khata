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
import com.example.khataapp.views.sale.viewModel.SaleDocViewModel;
import com.example.khataapp.views.sale.viewModel.SaleReturnDocViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductSaleRecyclerAdapter extends RecyclerView.Adapter<ProductSaleRecyclerAdapter.SaleProductViewHolder> {

    private final List<Item> itemList ;
    private LayoutInflater layoutInflater;
    private SaleReturnDocViewModel saleReturnDocViewModel;
    private SaleDocViewModel saleViewModel;
    private int key;

    public ProductSaleRecyclerAdapter(SaleReturnDocViewModel saleReturnDocViewModel, SaleDocViewModel saleViewModel, int key )
    {
        itemList = new ArrayList<>();
        if (saleReturnDocViewModel!=null)
        {
            this.saleReturnDocViewModel= saleReturnDocViewModel;

        }
        if (saleViewModel!=null)
        {
            this.saleViewModel = saleViewModel;

        }

        this.key= key;
    }

    @NonNull
    @Override
    public SaleProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        SaleItemCardBinding binding = SaleItemCardBinding.inflate(layoutInflater,parent,false);


        return new SaleProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleProductViewHolder holder, int position) {

        Item item = itemList.get(position);

        if (key==1)
        {
            holder.mBinding.setSaleReturnViewModel(saleReturnDocViewModel);

        }
        else
        {
            holder.mBinding.setSaleViewModel(saleViewModel);

        }
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
            if (key==1)
            {
                saleReturnDocViewModel.getSubTotalAmount().set(String.valueOf(Item.totalAmount));
                saleReturnDocViewModel.getTotalQty().set(String.valueOf(Item.totalQty));
                try
                {
                    if (saleReturnDocViewModel.getGstFlag().get())
                    {
                        double stAmount= Item.totalAmount;
                        double gstPercentage= (17*stAmount)/100;
                        double totalAmount= stAmount+gstPercentage;
                        saleReturnDocViewModel.getTotalAmount().set(String.valueOf(totalAmount));
                        saleReturnDocViewModel.getGstTax().set(String.valueOf(gstPercentage));

                    }
                    else
                    {
                        saleReturnDocViewModel.getTotalAmount().set(String.valueOf(Item.totalAmount));
                    }
                }
                catch (Exception e)
                {
                    Log.e(ProductSaleRecyclerAdapter.class.getSimpleName(),e.toString());
                }

            }
            else
            {
                saleViewModel.getSubTotalAmount().set(String.valueOf(Item.totalAmount));
                saleViewModel.getTotalQty().set(String.valueOf(Item.totalQty));
                try
                {
                    if (saleViewModel.getGstFlag().get())
                    {
                        double stAmount= Item.totalAmount;
                        double gstPercentage= (17*stAmount)/100;
                        double totalAmount= stAmount+gstPercentage;
                        saleViewModel.getTotalAmount().set(String.valueOf(totalAmount));
                        saleViewModel.getGstTax().set(String.valueOf(gstPercentage));

                    }
                    else
                    {
                        saleViewModel.getTotalAmount().set(String.valueOf(Item.totalAmount));
                    }
                }
                catch (Exception e)
                {
                    Log.e(ProductSaleRecyclerAdapter.class.getSimpleName(),e.toString());
                }
            }


            itemList.remove(item);
            notifyDataSetChanged();
        }
    }

    public List<Item> getItemList() {
        List<Item> list= new ArrayList<>();
        for (Item model:itemList)
        {
            model.setProductImage("");
            list.add(model);
        }
        return list;
    }

    public void clearList()
    {
        itemList.clear();
        notifyDataSetChanged();
    }


    public boolean checkItemExists(Item item)
    {
        return itemList.contains(item);
    }

    public  class SaleProductViewHolder extends RecyclerView.ViewHolder
    {
        SaleItemCardBinding mBinding;
        public SaleProductViewHolder(@NonNull SaleItemCardBinding binding) {
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
                    if (key==1)
                    {
                        saleReturnDocViewModel.getToastMessage().setValue("Edit Mode Enable");

                    }
                    else
                    {
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

                    if (key==1)
                    {
                        saleReturnDocViewModel.getToastMessage().setValue("Item Edit Successfully");
                        saleReturnDocViewModel.getSubTotalAmount().set(String.valueOf(Item.totalAmount));
                        saleReturnDocViewModel.getTotalQty().set(String.valueOf(Item.totalQty));
                    }else
                    {
                        saleViewModel.getToastMessage().setValue("Item Edit Successfully");
                        saleViewModel.getSubTotalAmount().set(String.valueOf(Item.totalAmount));
                        saleViewModel.getTotalQty().set(String.valueOf(Item.totalQty));
                    }


                    if (key==1)
                    {
                        try
                        {
                            if (saleReturnDocViewModel.getGstFlag().get())
                            {
                                double gstPercentage= (17*Item.totalAmount)/100;
                                saleReturnDocViewModel.getGstTax().set(String.valueOf(gstPercentage));
                                saleReturnDocViewModel.getTotalAmount().set(String.valueOf(Item.totalAmount+gstPercentage));

                            }
                            else
                            {
                                saleReturnDocViewModel.getTotalAmount().set(String.valueOf(Item.totalAmount));
                            }
                        }
                        catch (Exception e)
                        {
                            Log.e(ProductSaleRecyclerAdapter.class.getSimpleName(),e.toString());
                        }
                    }
                    else
                    {
                        try
                        {
                            if (saleViewModel.getGstFlag().get())
                            {
                                double gstPercentage= (17*Item.totalAmount)/100;
                                saleViewModel.getGstTax().set(String.valueOf(gstPercentage));
                                saleViewModel.getTotalAmount().set(String.valueOf(Item.totalAmount+gstPercentage));

                            }
                            else
                            {
                                saleViewModel.getTotalAmount().set(String.valueOf(Item.totalAmount));
                            }
                        }
                        catch (Exception e)
                        {
                            Log.e(ProductSaleRecyclerAdapter.class.getSimpleName(),e.toString());
                        }
                    }


                }
            });
        }
    }
}
