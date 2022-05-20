package com.example.khataapp.views.stockAdjustment.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.StockAdjustmentCardBinding;
import com.example.khataapp.models.Document;
import com.example.khataapp.views.stockAdjustment.viewModel.StockAdjustmentListViewModel;

import java.util.ArrayList;
import java.util.List;

public class StockeAdjustListAdapter extends RecyclerView.Adapter<StockeAdjustListAdapter.StockAdjustmentListViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Document> documentList;
    private StockAdjustmentListViewModel viewModel;

    public StockeAdjustListAdapter(StockAdjustmentListViewModel viewModel) {
        documentList = new ArrayList<>();

        if (viewModel != null) {
            this.viewModel = viewModel;
        }
    }

    @NonNull
    @Override
    public StockAdjustmentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        StockAdjustmentCardBinding binding = StockAdjustmentCardBinding.inflate(layoutInflater, parent, false);

        return new StockAdjustmentListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdjustmentListViewHolder holder, int position) {

        Document document = new Document();

        document = documentList.get(position);

        holder.mBinding.setDocument(document);

        holder.mBinding.setViewModel(viewModel);


        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public void setDocumentList(List<Document> list) {
        documentList.clear();
        if (list != null && list.size() > 0) {
            documentList.addAll(list);
        }

        notifyDataSetChanged();

    }

    public static class StockAdjustmentListViewHolder extends RecyclerView.ViewHolder {

        StockAdjustmentCardBinding mBinding;

        public StockAdjustmentListViewHolder(@NonNull StockAdjustmentCardBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }
    }
}
