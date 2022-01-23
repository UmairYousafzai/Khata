package com.example.khataapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khataapp.databinding.CountryCardBinding;
import com.example.khataapp.models.Location;

import java.util.ArrayList;
import java.util.List;

public class PartyListAdapter extends RecyclerView.Adapter<PartyListAdapter.PartyViewHolder>{


    private LayoutInflater layoutInflater;
    private List<Location> locationList= new ArrayList<>();
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

        CountryCardBinding binding = CountryCardBinding.inflate(layoutInflater,parent,false);
        return new PartyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {

        Location location = new Location();
        location = locationList.get(position);

        if (location.getName()!=null)
        {
            holder.mBinding.tvLocation.setText(location.getName());

        }


    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public void setLocationList(List<Location> list)
    {
        if (list!=null)
        {
            locationList = list;
        }
        else
        {
            locationList.clear();

        }
        notifyDataSetChanged();
    }

    public class PartyViewHolder extends RecyclerView.ViewHolder
    {
        CountryCardBinding mBinding;

        public PartyViewHolder(@NonNull CountryCardBinding binding) {
            super(binding.getRoot());

            mBinding= binding;
            mBinding.locationCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(locationList.size()>0)
                    {
                        listener.onClick(locationList.get(getAdapterPosition()));
                        notifyDataSetChanged();
                    }


                }
            });
        }
    }


    public interface  SetOnClickListener
    {
        public void onClick(Location location);
    }

}
