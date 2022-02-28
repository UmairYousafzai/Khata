package com.example.khataapp.purchase;

import static com.example.khataapp.utils.CONSTANTS.ITEM;
import static com.example.khataapp.utils.CONSTANTS.SEARCH_ITEMS_BTN;
import static com.example.khataapp.utils.CONSTANTS.SEARCH_SUPPLIER_BTN;
import static com.example.khataapp.utils.CONSTANTS.SUPPLIER;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.khataapp.R;
import com.example.khataapp.databinding.CustomProductDialogBinding;
import com.example.khataapp.databinding.FragmentPurchaseBinding;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.purchase.viewmodel.PurchaseViewModel;

import java.util.List;
import java.util.Objects;


public class PurchaseFragment extends Fragment {

    private FragmentPurchaseBinding mBinding;
    private NavController navController;
    private PurchaseViewModel viewModel;
    private AlertDialog alertDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentPurchaseBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel= new ViewModelProvider(this).get(PurchaseViewModel.class);
        navController = NavHostFragment.findNavController(this);

        mBinding.setViewModel(viewModel);

        getLiveData();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.getBtnAction().setValue(0);
    }

    private void getLiveData() {

        viewModel.getBtnAction().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer action) {

                if (action==2)
                {
                    navController.navigate(R.id.action_purchaseFragment_to_purchaseListFragment);
                }
                else if (action== SEARCH_SUPPLIER_BTN)
                {
                    mBinding.supplierSpinner.setFocusableInTouchMode(true);
                    mBinding.supplierSpinner.setInputType(InputType.TYPE_CLASS_TEXT);
                    openKeyBoard(mBinding.supplierSpinner);

                }  else if (action== SEARCH_ITEMS_BTN)
                {
                    mBinding.itemSpinner.setFocusableInTouchMode(true);
                    mBinding.itemSpinner.setInputType(InputType.TYPE_CLASS_TEXT);
                    openKeyBoard(mBinding.itemSpinner);
                }
            }
        });

        viewModel.getItemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                if (item!=null)
                {
                    showProductDialog();
                }
            }
        });

    }

    private void showProductDialog() {

        CustomProductDialogBinding dialogBinding = CustomProductDialogBinding.inflate(getLayoutInflater());

         alertDialog= new AlertDialog.Builder(requireContext()).setView(dialogBinding.getRoot())
                .setCancelable(false)
                .create();

        dialogBinding.setViewModel(viewModel);

        alertDialog.show();

        viewModel.getDialogDismiss().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==1)
                {
                    alertDialog.dismiss();
                    viewModel.getDialogDismiss().setValue(0);

                }


            }
        });
    }


    public void openKeyBoard(View view)
    {
        if (view.requestFocus())
        {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }

    }

    public void closeKeyBoard()
    {
        View view = requireActivity().getCurrentFocus();

        if (view!=null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);

        }


    }
}