package com.example.khataapp.views.purchase;

import static com.example.khataapp.utils.CONSTANTS.AUTHORIZE_BTN;
import static com.example.khataapp.utils.CONSTANTS.DATE_BTN;
import static com.example.khataapp.utils.CONSTANTS.NEW_BTN;
import static com.example.khataapp.utils.CONSTANTS.DOCUMENT;
import static com.example.khataapp.utils.CONSTANTS.SEARCH_ITEMS_BTN;
import static com.example.khataapp.utils.CONSTANTS.SEARCH_SUPPLIER_BTN;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.khataapp.R;
import com.example.khataapp.databinding.CustomProductDialogBinding;
import com.example.khataapp.databinding.FragmentPurchaseBinding;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.Item;
import com.example.khataapp.utils.DateUtil;
import com.example.khataapp.utils.DialogUtil;
import com.example.khataapp.views.purchase.viewmodel.PurchaseViewModel;

import java.util.Calendar;


public class PurchaseFragment extends Fragment {

    private FragmentPurchaseBinding mBinding;
    private NavController navController;
    private PurchaseViewModel viewModel;
    private AlertDialog progressDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentPurchaseBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());


        viewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
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
                    PurchaseFragmentDirections.ActionPurchaseFragmentToPurchaseListFragment navDirection =
                            PurchaseFragmentDirections.actionPurchaseFragmentToPurchaseListFragment("Purchase Doc List");
                    navDirection.setPurchaseType(1);
                    navController.navigate(navDirection);
                } else if (action == SEARCH_SUPPLIER_BTN) {
                    mBinding.supplierSpinner.setFocusableInTouchMode(true);
                    mBinding.supplierSpinner.setInputType(InputType.TYPE_CLASS_TEXT);
                    mBinding.supplierSpinner.setText("");
                    openKeyBoard(mBinding.supplierSpinner);

                } else if (action == SEARCH_ITEMS_BTN) {
                    mBinding.itemSpinner.setFocusableInTouchMode(true);
                    mBinding.itemSpinner.setInputType(InputType.TYPE_CLASS_TEXT);
                    mBinding.itemSpinner.setText("");

                    openKeyBoard(mBinding.itemSpinner);
                } else if (action == DATE_BTN) {
                    openDateDialog();
                } else if (action == NEW_BTN) {
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
                        viewModel.getPurchaseByDocCode(document.getDocNo());
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
        mBinding.supplierSpinner.setText("");
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

    private void openDateDialog() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int checkMonth = (month) % 10, checkDay = (dayOfMonth % 10);

                String mMonth, mDay;
                if (checkMonth > 0 && month < 9) {
                    mMonth = "0" + (month);
                } else {
                    mMonth = String.valueOf(month);

                }

                if (checkDay > 0 && dayOfMonth < 10) {
                    mDay = "0" + (dayOfMonth);

                } else {

                    mDay = String.valueOf(dayOfMonth);
                }
                String date = year + "-" + mMonth + "-" + mDay;
                viewModel.getDate().set(date);
                viewModel.getIsEdit().setValue(true);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showProductDialog() {

        CustomProductDialogBinding dialogBinding = CustomProductDialogBinding.inflate(getLayoutInflater());

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
        mBinding.supplierSpinner.setEnabled(false);
        mBinding.btnSearchSupplier.setEnabled(false);
        mBinding.btnSearchProduct.setEnabled(false);
        mBinding.itemSpinner.setEnabled(false);
        mBinding.tvAuthorize.setEnabled(false);
        mBinding.tvPDF.setEnabled(false);
        mBinding.tvRetrieve.setEnabled(false);
        mBinding.gstCheckBox.setEnabled(false);
        mBinding.newFab.setEnabled(false);
        mBinding.saveFab.setEnabled(false);
        mBinding.rvListPurchase.setEnabled(false);
    }
}