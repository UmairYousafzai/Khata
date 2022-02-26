package com.example.khataapp.purchase;

import static com.example.khataapp.utils.CONSTANTS.SUPPLIER;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.example.khataapp.databinding.CustomAlertDialogBinding;
import com.example.khataapp.databinding.FragmentSupplierListBinding;
import com.example.khataapp.models.Party;
import com.example.khataapp.purchase.viewmodel.PartyViewModel;
import com.example.khataapp.utils.CONSTANTS;

import java.util.Objects;

public class SupplierListFragment extends Fragment {

    private FragmentSupplierListBinding mBinding;
    private NavController navController;
    private PartyViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       mBinding = FragmentSupplierListBinding.inflate(inflater,container,false);
       return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController= NavHostFragment.findNavController(this);
        viewModel= new ViewModelProvider(this).get(PartyViewModel.class);

        mBinding.setViewModel(viewModel);

        getLiveData();
    }

    private void getLiveData() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Supplier");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        viewModel.getSuppliers();

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {

                if (message!=null)
                {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getProgressMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer progress) {

                if (progress==1)
                {
                    progressDialog.dismiss();
                }
            }
        });

        viewModel.getPartyMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Party>() {
            @Override
            public void onChanged(Party party) {

                if (party!=null)
                {
                    showConfirmationDialog(party);
                }
            }
        });

    }

    private void showConfirmationDialog(Party party) {

        CustomAlertDialogBinding dialogBinding = CustomAlertDialogBinding.inflate(getLayoutInflater());

        AlertDialog dialog = new AlertDialog.Builder(requireContext()).setView(dialogBinding.getRoot()).setCancelable(false).create();

        dialogBinding.title.setText("Supplier");
        dialogBinding.body.setText("Are you sure ?");

        dialog.show();

        dialogBinding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                Objects.requireNonNull(navController.getPreviousBackStackEntry()).getSavedStateHandle().set(SUPPLIER,party);
                navController.popBackStack();

            }
        });

        dialogBinding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }
}