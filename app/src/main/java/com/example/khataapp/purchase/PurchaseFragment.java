package com.example.khataapp.purchase;

import static com.example.khataapp.utils.CONSTANTS.CHECK_BOX_BTN;
import static com.example.khataapp.utils.CONSTANTS.DATE_BTN;
import static com.example.khataapp.utils.CONSTANTS.ITEM;
import static com.example.khataapp.utils.CONSTANTS.SEARCH_ITEMS_BTN;
import static com.example.khataapp.utils.CONSTANTS.SEARCH_SUPPLIER_BTN;
import static com.example.khataapp.utils.CONSTANTS.SUPPLIER;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
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
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.purchase.viewmodel.PurchaseViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class PurchaseFragment extends Fragment {

    private FragmentPurchaseBinding mBinding;
    private NavController navController;
    private PurchaseViewModel viewModel;


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
        Calendar calendar = Calendar.getInstance();
        int day= calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        mBinding.tvDate.setText(year +"-" +month+"-" + day);
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

                if (action==2)
                {
                    navController.navigate(R.id.action_purchaseFragment_to_purchaseListFragment);
                }
                else if (action== SEARCH_SUPPLIER_BTN)
                {
                    mBinding.supplierSpinner.setFocusableInTouchMode(true);
                    mBinding.supplierSpinner.setInputType(InputType.TYPE_CLASS_TEXT);
                    mBinding.supplierSpinner.setText("");
                    openKeyBoard(mBinding.supplierSpinner);

                }  else if (action== SEARCH_ITEMS_BTN)
                {
                    mBinding.itemSpinner.setFocusableInTouchMode(true);
                    mBinding.itemSpinner.setInputType(InputType.TYPE_CLASS_TEXT);
                    mBinding.itemSpinner.setText("");

                    openKeyBoard(mBinding.itemSpinner);
                }
                else if (action== DATE_BTN)
                {
                    openDateDialog();
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

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s!=null)
                {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getIsEdit().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEdit) {

                Drawable drawable;
                if (isEdit)
                {
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_done_24, null);

                    mBinding.floatingActionButton.setText("Save");

                }
                else
                {
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_add_24, null);

                    mBinding.floatingActionButton.setText("ADD");
                }
                mBinding.floatingActionButton.setIcon(drawable);
            }
        });

    }

    private void openDateDialog() {

        Calendar calendar = Calendar.getInstance();
        int day= calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog= new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int checkMonth = (month ) % 10, checkDay = (dayOfMonth % 10);

                String mMonth, mDay;
                if (checkMonth > 0 && month < 9) {
                    mMonth = "0" + (month );
                } else {
                    mMonth = String.valueOf(month);

                }

                if (checkDay > 0 && dayOfMonth < 10) {
                    mDay = "0" + (dayOfMonth);

                } else {

                    mDay = String.valueOf(dayOfMonth);
                }
                String date= year +"-"+ mMonth +"-" +mDay;
                mBinding.tvDate.setText(date);
                viewModel.getDate().setValue(date);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void showProductDialog() {

        CustomProductDialogBinding dialogBinding = CustomProductDialogBinding.inflate(getLayoutInflater());

       AlertDialog  alertDialog= new AlertDialog.Builder(requireContext()).setView(dialogBinding.getRoot())
                .setCancelable(false)
                .create();

        dialogBinding.setViewModel(viewModel);

        alertDialog.show();

        dialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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