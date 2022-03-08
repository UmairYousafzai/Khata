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
import com.example.khataapp.sale.viewModel.SaleDocViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    private final List<Item> itemList ;
    private LayoutInflater layoutInflater;
    private PurchaseViewModel purchaseViewModel;
    private SaleDocViewModel saleViewModel;
    private int key;

    public ProductRecyclerAdapter(PurchaseViewModel purchaseViewModel, SaleDocViewModel saleViewModel,int key )
    {
        itemList = new ArrayList<>();
        if (purchaseViewModel!=null)
        {
            this.purchaseViewModel= purchaseViewModel;

        }
        if (saleViewModel!=null)
        {
            this.saleViewModel = saleViewModel;

        }

        this.key= key;
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

        if (key==1)
        {
            holder.mBinding.setPurchaseViewModel(purchaseViewModel);

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
                purchaseViewModel.getSubTotalAmount().set(String.valueOf(Item.totalAmount));
                purchaseViewModel.getTotalQty().set(String.valueOf(Item.totalQty));
                try
                {
                    if (purchaseViewModel.getGstFlag().get())
                    {
                        double stAmount= Item.totalAmount;
                        double gstPercentage= (17*stAmount)/100;
                        double totalAmount= stAmount+gstPercentage;
                        purchaseViewModel.getTotalAmount().set(String.valueOf(totalAmount));
                        purchaseViewModel.getGstTax().set(String.valueOf(gstPercentage));

                    }
                    else
                    {
                        purchaseViewModel.getTotalAmount().set(String.valueOf(Item.totalAmount));
                    }
                }
                catch (Exception e)
                {
                    Log.e(ProductRecyclerAdapter.class.getSimpleName(),e.toString());
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
                    Log.e(ProductRecyclerAdapter.class.getSimpleName(),e.toString());
                }
            }


            itemList.remove(item);
            notifyDataSetChanged();
        }
    }

    public List<Item> getItemList() {
        return itemList;
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
                    if (key==1)
                    {
                        purchaseViewModel.getToastMessage().setValue("Edit Mode Enable");

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
                        purchaseViewModel.getToastMessage().setValue("Item Edit Successfully");
                        purchaseViewModel.getSubTotalAmount().set(String.valueOf(Item.totalAmount));
                        purchaseViewModel.getTotalQty().set(String.valueOf(Item.totalQty));
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
                            if (purchaseViewModel.getGstFlag().get())
                            {
                                double gstPercentage= (17*Item.totalAmount)/100;
                                purchaseViewModel.getGstTax().set(String.valueOf(gstPercentage));
                                purchaseViewModel.getTotalAmount().set(String.valueOf(Item.totalAmount+gstPercentage));

                            }
                            else
                            {
                                purchaseViewModel.getTotalAmount().set(String.valueOf(Item.totalAmount));
                            }
                        }
                        catch (Exception e)
                        {
                            Log.e(ProductRecyclerAdapter.class.getSimpleName(),e.toString());
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
                            Log.e(ProductRecyclerAdapter.class.getSimpleName(),e.toString());
                        }
                    }


                }
            });
        }
    }
}
