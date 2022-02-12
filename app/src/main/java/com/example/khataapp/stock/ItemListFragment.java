package com.example.khataapp.stock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.khataapp.R;
import com.example.khataapp.databinding.FragmentItemListBinding;
import com.example.khataapp.models.Item;
import com.example.khataapp.stock.viewmodel.StockViewModel;

public class ItemListFragment extends Fragment {

    private FragmentItemListBinding mBinding;
    private StockViewModel viewModel;
    private NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentItemListBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController= NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this).get(StockViewModel.class);

        mBinding.setViewModel(viewModel);

        viewModel.getItemsList();

        getLiveData();

        btnListener();
    }

    private void btnListener() {

        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_itemListFragment_to_addItemFragment);
            }
        });
    }

    private void getLiveData() {

        viewModel.getServerErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s!=null)
                {
                    Toast.makeText(requireContext(), ""+s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getItemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(Item item) {

                if (item!=null)
                {
                    ItemListFragmentDirections.ActionItemListFragmentToAddItemFragment action=
                            ItemListFragmentDirections.actionItemListFragmentToAddItemFragment();

                    action.setItem(item);
                    navController.navigate(action);
                    viewModel.getItemMutableLiveData().setValue(null);
                }
            }
        });

    }
}