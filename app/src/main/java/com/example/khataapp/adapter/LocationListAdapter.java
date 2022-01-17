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

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>{


    private LayoutInflater layoutInflater;
    private List<Location> locationList= new ArrayList<>();
    private SetOnClickListener listener;

    public void SetOnClickListener(SetOnClickListener listener)
    {
        this.listener = listener;
    }



    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        CountryCardBinding binding = CountryCardBinding.inflate(layoutInflater,parent,false);
        return new LocationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {

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

    public class LocationViewHolder extends RecyclerView.ViewHolder
    {
        CountryCardBinding mBinding;

        public LocationViewHolder(@NonNull CountryCardBinding binding) {
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
