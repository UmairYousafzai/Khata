package com.example.khataapp.purchase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.adapter.PartyListAdapter;
import com.example.khataapp.databinding.PartyCardBinding;
import com.example.khataapp.databinding.PartyCardTwoBinding;
import com.example.khataapp.models.Party;
import com.example.khataapp.purchase.viewmodel.PartyViewModel;

import java.util.ArrayList;
import java.util.List;

public class PartyRecyclerAdapter extends RecyclerView.Adapter<PartyRecyclerAdapter.PartyViewHolder> implements Filterable {

    private LayoutInflater layoutInflater;
    private final List<Party> partyListFull = new ArrayList<>();
    private final List<Party> partyList = new ArrayList<>();
    private PartyViewModel viewModel;

    public PartyRecyclerAdapter( PartyViewModel viewModel) {
       this.viewModel= viewModel;
    }



    @NonNull
    @Override
    public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        PartyCardTwoBinding binding = PartyCardTwoBinding.inflate(layoutInflater,parent,false);
        return new PartyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {

        Party party = new Party();
        party= partyList.get(position);
        holder.mBinding.setParty(party);
        holder.mBinding.setViewModel(viewModel);
        holder.mBinding.executePendingBindings();



        if (position==partyList.size()-1)
        {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,200);
            holder.mBinding.partyCard.setLayoutParams(layoutParams);
        }
        else
        {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,0);
            holder.mBinding.partyCard.setLayoutParams(layoutParams);
        }

    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public void setPartyListFull(List<Party> list)
    {
        if (list!=null)
        {
            partyListFull.clear();
            partyList.clear();
            partyListFull.addAll(list);
            partyList.addAll(list);
        }
        else
        {
            partyListFull.clear();

        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Party> filterList= new ArrayList<>();
            if (constraint!=null&&constraint.length()>0)
            {
                String filterPattern= constraint.toString().toLowerCase().trim();
                for (Party party:partyListFull)
                {
                    if (party.getPartyName().toLowerCase().trim().contains(filterPattern))
                    {
                        filterList.add(party);
                    }
                }
            }
            else
            {
                filterList.addAll( partyListFull);
            }

            FilterResults filterResults= new FilterResults();
            filterResults.values= filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            partyList.clear();
            partyList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class PartyViewHolder extends RecyclerView.ViewHolder
    {
        PartyCardTwoBinding mBinding;

        public PartyViewHolder(@NonNull PartyCardTwoBinding binding) {
            super(binding.getRoot());

            mBinding= binding;


        }
    }



}
