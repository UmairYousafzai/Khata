package com.example.khataapp.views.stockAdjustment;

import static com.example.khataapp.utils.CONSTANTS.DOCUMENT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.khataapp.databinding.FragmentStockAdjustListBinding;
import com.example.khataapp.models.Document;
import com.example.khataapp.utils.DialogUtil;
import com.example.khataapp.views.sale.SaleDocListFragmentArgs;
import com.example.khataapp.views.stockAdjustment.viewModel.StockAdjustmentListViewModel;

public class StockAdjustListFragment extends Fragment {


    private FragmentStockAdjustListBinding mBinding;
    private StockAdjustmentListViewModel viewModel;
    private NavController navController;
    private AlertDialog progressDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentStockAdjustListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(StockAdjustmentListViewModel.class);
        navController = NavHostFragment.findNavController(this);
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());

        mBinding.setViewModel(viewModel);


        getLiveData();
    }

    private void getLiveData() {
        viewModel.getStockAdjustList();
            ((AppCompatActivity)requireActivity()).getSupportActionBar().setTitle("Stock Adjustment List");


        viewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showProgressDialog) {

                if (showProgressDialog) {
                    progressDialog.show();
                } else {
                    progressDialog.dismiss();
                }
            }
        });

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {

                if (message != null) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getStockAdjustMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Document>() {
            @Override
            public void onChanged(Document document) {
                if (document != null) {
                    if (navController.getPreviousBackStackEntry() != null) {
                        navController.getPreviousBackStackEntry().getSavedStateHandle().set(DOCUMENT, document);
                        navController.popBackStack();

                    }
                }
            }
        });
    }
}