package com.example.khataapp.views.party.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.CardViewPurchaseDocBinding;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.response.voucher.VoucherDetail;
import com.example.khataapp.viewModel.PartyViewModel;
import com.example.khataapp.views.purchase.viewmodel.PurchaseListViewModel;

import java.util.ArrayList;
import java.util.List;

public class VoucherDetailAdapter extends RecyclerView.Adapter<VoucherDetailAdapter.VoucherDetailViewHolder> {


    private LayoutInflater layoutInflater;
    private List<VoucherDetail> voucherDetailList;
    private PartyViewModel viewModel;

    public VoucherDetailAdapter(PartyViewModel viewModel) {
        voucherDetailList = new ArrayList<>();

        if (viewModel != null) {
            this.viewModel = viewModel;
        }
    }

    @NonNull
    @Override
    public VoucherDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        CardViewPurchaseDocBinding binding = CardViewPurchaseDocBinding.inflate(layoutInflater, parent, false);

        return new VoucherDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherDetailViewHolder holder, int position) {

        VoucherDetail voucherDetail = voucherDetailList.get(position);

        holder.mBinding.setVoucherDetail(voucherDetail);

        holder.mBinding.setViewModel(viewModel);

        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return voucherDetailList.size();
    }

    public void setVoucherDetailList(List<VoucherDetail> list) {
        voucherDetailList.clear();
        if (list != null && list.size() > 0) {
            voucherDetailList.addAll(list);
        }

        notifyDataSetChanged();

    }

    public static class VoucherDetailViewHolder extends RecyclerView.ViewHolder {

        CardViewPurchaseDocBinding mBinding;

        public VoucherDetailViewHolder(@NonNull CardViewPurchaseDocBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }
    }
}
