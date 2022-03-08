package com.example.khataapp.purchase;

import static com.example.khataapp.utils.CONSTANTS.PURCHASE;

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

import com.example.khataapp.databinding.FragmentPurchaseListBinding;
import com.example.khataapp.models.Purchase;
import com.example.khataapp.purchase.viewmodel.PurchaseListViewModel;
import com.example.khataapp.utils.CONSTANTS;


public class PurchaseListFragment extends Fragment {

    private FragmentPurchaseListBinding mBinding;
    private PurchaseListViewModel viewModel;
    private NavController navController ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentPurchaseListBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel= new ViewModelProvider(this).get(PurchaseListViewModel.class);
        navController= NavHostFragment.findNavController(this);

        mBinding.setViewModel(viewModel);

        viewModel.getPurchasesList();

        getLiveData();
    }

    private void getLiveData() {

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {

                if (message!=null)
                {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getPurchaseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Purchase>() {
            @Override
            public void onChanged(Purchase purchase) {
                if (purchase!=null)
                {
                    if (navController.getPreviousBackStackEntry()!=null)
                    {
                        navController.getPreviousBackStackEntry().getSavedStateHandle().set(PURCHASE,purchase);
                        navController.popBackStack();

                    }
                }
            }
        });
    }
}