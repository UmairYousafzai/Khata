package com.example.khataapp.sale.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.PurchaseCardBinding;
import com.example.khataapp.databinding.SaleCardBinding;
import com.example.khataapp.models.Document;
import com.example.khataapp.purchase.viewmodel.PurchaseListViewModel;
import com.example.khataapp.sale.viewModel.SaleDocListViewModel;
import com.example.khataapp.sale.viewModel.SaleReturnDocViewModel;

import java.util.ArrayList;
import java.util.List;

public class SaleDocListListAdapter extends RecyclerView.Adapter<SaleDocListListAdapter.SaleListListViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Document> documentList;
    private SaleDocListViewModel saleViewModel;

    public SaleDocListListAdapter( SaleDocListViewModel saleViewModel) {
        documentList = new ArrayList<>();

            this.saleViewModel= saleViewModel;


    }

    @NonNull
    @Override
    public SaleListListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null)
        {
            layoutInflater= LayoutInflater.from(parent.getContext());
        }

        SaleCardBinding binding = SaleCardBinding.inflate(layoutInflater,parent,false);

        return new SaleListListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleListListViewHolder holder, int position) {

        Document document = new Document();

        document = documentList.get(position);

        holder.mBinding.setDocument(document);

            holder.mBinding.setSaleViewModel(saleViewModel);



        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public void setPurchaseList(List<Document> list) {
        documentList.clear();
        if (list!=null && list.size()>0)
        {
            documentList.addAll(list);
        }

        notifyDataSetChanged();

    }

    public static class SaleListListViewHolder extends RecyclerView.ViewHolder
    {

        SaleCardBinding mBinding;
        public SaleListListViewHolder(@NonNull SaleCardBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }
    }
}
