package com.example.khataapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.CountryCardBinding;
import com.example.khataapp.databinding.PartyCardBinding;
import com.example.khataapp.models.Location;
import com.example.khataapp.models.Party;

import java.util.ArrayList;
import java.util.List;

public class PartyListAdapter extends RecyclerView.Adapter<PartyListAdapter.PartyViewHolder>{


    private LayoutInflater layoutInflater;
    private List<Party> partyList= new ArrayList<>();
    private SetOnClickListener listener;

    public void SetOnClickListener(SetOnClickListener listener)
    {
        this.listener = listener;
    }



    @NonNull
    @Override
    public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        PartyCardBinding binding = PartyCardBinding.inflate(layoutInflater,parent,false);
        return new PartyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {

        Party party = new Party();
        party= partyList.get(position);
        holder.mBinding.setParty(party);
        holder.mBinding.executePendingBindings();


    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public void setPartyList(List<Party> list)
    {
        if (list!=null)
        {
            partyList = list;
        }
        else
        {
            partyList.clear();

        }
        notifyDataSetChanged();
    }

    public class PartyViewHolder extends RecyclerView.ViewHolder
    {
        PartyCardBinding mBinding;

        public PartyViewHolder(@NonNull PartyCardBinding binding) {
            super(binding.getRoot());

            mBinding= binding;

            mBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition()!=RecyclerView.NO_POSITION&&listener!=null)
                    {
                        listener.onClick(partyList.get(getAdapterPosition()));
                    }
                }
            });

        }
    }


    public interface  SetOnClickListener
    {
        public void onClick(Party party);
    }

}
