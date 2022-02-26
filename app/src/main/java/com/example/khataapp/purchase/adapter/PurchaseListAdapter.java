package com.example.khataapp.purchase.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.PurchaseCardBinding;
import com.example.khataapp.models.Purchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.PurchaseListViewModel> {

    private LayoutInflater layoutInflater;
    private List<Purchase> purchaseList;
    private com.example.khataapp.purchase.viewmodel.PurchaseListViewModel viewModel;

    public PurchaseListAdapter(com.example.khataapp.purchase.viewmodel.PurchaseListViewModel viewModel) {
        purchaseList = new ArrayList<>();
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public PurchaseListViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null)
        {
            layoutInflater= LayoutInflater.from(parent.getContext());
        }

        PurchaseCardBinding binding = PurchaseCardBinding.inflate(layoutInflater,parent,false);

        return new PurchaseListViewModel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseListViewModel holder, int position) {

        Purchase purchase = new Purchase();

        purchase= purchaseList.get(position);

        holder.mBinding.setPurchase(purchase);
        holder.mBinding.setViewModel(viewModel);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public void setPurchaseList(List<Purchase> list) {
        purchaseList.clear();
        if (list!=null && list.size()>0)
        {
            purchaseList.addAll(list);
        }

        notifyDataSetChanged();

    }

    public static class PurchaseListViewModel extends RecyclerView.ViewHolder
    {

        PurchaseCardBinding mBinding;
        public PurchaseListViewModel(@NonNull PurchaseCardBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }
    }
}
