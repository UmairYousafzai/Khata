package com.example.khataapp.views.purchase.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.PurchaseCardBinding;
import com.example.khataapp.models.Document;
import com.example.khataapp.views.purchase.viewmodel.PurchaseListViewModel;

import java.util.ArrayList;
import java.util.List;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.PurchaseListViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Document> documentList;
    private PurchaseListViewModel purchaseViewModel;

    public PurchaseListAdapter(PurchaseListViewModel purchaseViewModel) {
        documentList = new ArrayList<>();

        if (purchaseViewModel!=null)
        {
            this.purchaseViewModel=purchaseViewModel;
        }
    }

    @NonNull
    @Override
    public PurchaseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null)
        {
            layoutInflater= LayoutInflater.from(parent.getContext());
        }

        PurchaseCardBinding binding = PurchaseCardBinding.inflate(layoutInflater,parent,false);

        return new PurchaseListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseListViewHolder holder, int position) {

        Document document = new Document();

        document = documentList.get(position);

        holder.mBinding.setDocument(document);

            holder.mBinding.setPurchaseViewModel(purchaseViewModel);



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

    public static class PurchaseListViewHolder extends RecyclerView.ViewHolder
    {

        PurchaseCardBinding mBinding;
        public PurchaseListViewHolder(@NonNull PurchaseCardBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }
    }
}
