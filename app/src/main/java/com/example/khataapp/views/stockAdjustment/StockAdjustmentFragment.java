package com.example.khataapp.views.stockAdjustment;

import static com.example.khataapp.utils.CONSTANTS.DOCUMENT;
import static com.example.khataapp.utils.CONSTANTS.NEW_BTN;
import static com.example.khataapp.utils.CONSTANTS.SEARCH_ITEMS_BTN;
import static com.example.khataapp.utils.CONSTANTS.SEARCH_SUPPLIER_BTN;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.khataapp.databinding.CustomProductStockDialogBinding;
import com.example.khataapp.databinding.FragmentStockAdjustmentBinding;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.Item;
import com.example.khataapp.utils.DateUtil;
import com.example.khataapp.utils.DialogUtil;
import com.example.khataapp.views.stockAdjustment.viewModel.StockAdjustViewModel;

public class StockAdjustmentFragment extends Fragment {

    private FragmentStockAdjustmentBinding mBinding;
    private NavController navController;
    private StockAdjustViewModel viewModel;
    private AlertDialog progressDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentStockAdjustmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());


        viewModel = new ViewModelProvider(this).get(StockAdjustViewModel.class);
        navController = NavHostFragment.findNavController(this);

        mBinding.setViewModel(viewModel);

        getLiveData();
    }


    @Override
    public void onStop() {
        super.onStop();
        viewModel.getBtnAction().setValue(0);
        viewModel.getItemMutableLiveData().setValue(null);
        viewModel.getToastMessage().setValue(null);
    }

    private void getLiveData() {

        viewModel.getBtnAction().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer action) {

                if (action == 2) {
                    navController.navigate(StockAdjustmentFragmentDirections.actionStockAdjustmentFragmentToStockAdjustListFragment());
                } else if (action == SEARCH_SUPPLIER_BTN) {
                    mBinding.typeSpinner.setFocusableInTouchMode(true);
                    mBinding.typeSpinner.setInputType(InputType.TYPE_CLASS_TEXT);
                    mBinding.typeSpinner.setText("");
                    openKeyBoard(mBinding.typeSpinner);

                } else if (action == SEARCH_ITEMS_BTN) {
                    mBinding.itemSpinner.setFocusableInTouchMode(true);
                    mBinding.itemSpinner.setInputType(InputType.TYPE_CLASS_TEXT);
                    mBinding.itemSpinner.setText("");

                    openKeyBoard(mBinding.itemSpinner);
                }  else if (action == NEW_BTN) {
                    viewModel.getActionMutableLiveData().setValue("INSERT");
                    clearFields();
                }

            }
        });

        viewModel.getItemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                if (item != null) {
                    showProductDialog();

                }
            }
        });

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s != null) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getIsEdit().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEdit) {

                if (isEdit) {
                    mBinding.saveFab.setVisibility(View.VISIBLE);
                    mBinding.newFab.setVisibility(View.GONE);
                } else {
                    mBinding.newFab.setVisibility(View.VISIBLE);
                    mBinding.saveFab.setVisibility(View.GONE);

                }
            }
        });

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

        if (navController.getCurrentBackStackEntry() != null) {
            MutableLiveData<Document> purchaseLiveData = navController.getCurrentBackStackEntry()
                    .getSavedStateHandle()
                    .getLiveData(DOCUMENT);

            purchaseLiveData.observe(getViewLifecycleOwner(), new Observer<Document>() {
                @Override
                public void onChanged(Document document) {

                    if (document != null) {
                        viewModel.getStockAdjustmentByDocCode(document.getDocNo());
                        if (!document.getStatus().equals("Unauthorize"))
                        {
                            disableViews();

                        }
                    }
                }
            });
        }


    }

    private void clearFields() {

        mBinding.tvDate.setText(DateUtil.getInstance().getDate());
        mBinding.tvDocNumber.setText("---------");
        mBinding.typeSpinner.setText("");
        mBinding.itemSpinner.setText("");
        mBinding.tvTotalQty.setText("0");
        mBinding.tvSubTotal.setText("0");
        mBinding.tvAllTotal.setText("0");
        mBinding.gstCheckBox.setChecked(false);
        mBinding.tvTax.setText("0");
        viewModel.clearItemList();
        viewModel.getActionMutableLiveData().setValue("INSERT");
        viewModel.getIsEdit().setValue(false);


    }

    private void showProductDialog() {

        CustomProductStockDialogBinding dialogBinding = CustomProductStockDialogBinding.inflate(getLayoutInflater());

        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).setView(dialogBinding.getRoot())
                .setCancelable(false)
                .create();

        dialogBinding.setViewModel(viewModel);

        alertDialog.show();
        viewModel.getItemMutableLiveData().getValue().setQty(1);

        dialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getSelectedProductName().set("");

                alertDialog.dismiss();
            }
        });

        dialogBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

                viewModel.addItemToProductAdapter();
                mBinding.itemSpinner.setText("");
            }
        });


    }


    public void openKeyBoard(View view) {
        if (view.requestFocus()) {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }

    }

    public void closeKeyBoard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void disableViews()
    {
        mBinding.typeSpinner.setEnabled(false);
        mBinding.btnSearchSupplier.setEnabled(false);
        mBinding.btnSearchProduct.setEnabled(false);
        mBinding.itemSpinner.setEnabled(false);
        mBinding.tvAuthorize.setEnabled(false);
        mBinding.tvPDF.setEnabled(false);
        mBinding.gstCheckBox.setEnabled(false);
        mBinding.newFab.setEnabled(false);
        mBinding.saveFab.setEnabled(false);
        mBinding.rvListPurchase.setEnabled(false);
    }
}