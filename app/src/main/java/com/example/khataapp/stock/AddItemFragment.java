package com.example.khataapp.stock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.khataapp.databinding.FragmentAddItemBinding;
import com.example.khataapp.models.Department;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.ServerResponse;
import com.example.khataapp.stock.viewmodel.StockViewModel;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddItemFragment extends Fragment {


    private FragmentAddItemBinding mBinding;
    private StockViewModel stockViewModel;
    private HashMap<String, String> departmentHashMapForID = new HashMap<>();
    private HashMap<String, String> supplierHashMapForID = new HashMap<>();
    private ArrayList<String> supplierNameList = new ArrayList<>();
    private ArrayList<String> departmentNameList = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentAddItemBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stockViewModel = new ViewModelProvider(this).get(StockViewModel.class);


        getDepartment();
        getSupplier();
        btnListener();

    }

    private void btnListener() {

        mBinding.btnDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.btnDepartment.isChecked()) {
                    mBinding.departmentSpinnerLayout.setVisibility(View.VISIBLE);
                } else {
                    mBinding.departmentSpinnerLayout.setVisibility(View.GONE);

                }
            }
        });

        mBinding.btnSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.btnSupplier.isChecked()) {
                    mBinding.supplierSpinnerLayout.setVisibility(View.VISIBLE);
                } else {
                    mBinding.supplierSpinnerLayout.setVisibility(View.GONE);

                }
            }
        });

        mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveItem();
            }
        });
    }

    private void saveItem() {

        String unitSize,cartonSize,costPrice,salePrice;

        if (mBinding.etItemName.getText() != null && !mBinding.etItemName.getText().toString().equals("."))
        {

        }
        Item item = new Item();

        if (mBinding.etItemName.getText() != null && !mBinding.etItemName.getText().toString().isEmpty()) {
            mBinding.etItemNameLayout.setError(null);
            if (mBinding.etUnitSize.getText() != null && !mBinding.etUnitSize.getText().toString().equals("."))
            {
                mBinding.etUnitSizeLayout.setError(null);
                if (mBinding.etCartonSize.getText() != null && !mBinding.etCartonSize.getText().toString().equals("."))
                {
                    mBinding.etCartonSizeLayout.setError(null);
                    if (mBinding.etCostPrice.getText() != null && !mBinding.etCostPrice.getText().toString().equals("."))
                    {
                        mBinding.etCostPriceLayout.setError(null);
                        if (mBinding.etSalePrice.getText() != null && !mBinding.etSalePrice.getText().toString().equals("."))
                        {
                            mBinding.etSalePriceLayout.setError(null);


                            if (mBinding.etCartonSize.getText().toString().length()>0) {

                                    item.setCtnPcs(Double.parseDouble(mBinding.etCartonSize.getText().toString()));

                            }


                            if ( mBinding.etCostPrice.getText().toString().length()>0) {


                                    item.setUnitCost(Double.parseDouble(mBinding.etCostPrice.getText().toString()));

                            }

                            if (mBinding.etSalePrice.getText().toString().length()>0) {

                                    item.setUnitRetail(Double.parseDouble(mBinding.etSalePrice.getText().toString()));
                            }

                            if (departmentNameList!=null)
                            {
                                String name =departmentNameList.get(0);
                                item.setDepartmentCode(departmentHashMapForID.get(name));
                                item.setGroupCode(departmentHashMapForID.get(name));
                                item.setSubGroupCode(departmentHashMapForID.get(name));

                            }



                            if (mBinding.supplierSpinner.getText()!=null && mBinding.supplierSpinner.getText().toString().length()>0)
                            {
                                String name =mBinding.supplierSpinner.getText().toString();
                                item.setSupplierCode(supplierHashMapForID.get(name));

                            }
                            else
                            {
                                item.setSupplierCode("0");
                            }

                            item.setDescription(mBinding.etItemName.getText().toString());
                            item.setStatus("a");
                            item.setProductType("P");
                            item.setAction("INSERT");
                            item.setUserID(SharedPreferenceHelper.getInstance(requireContext()).getUserID());
                            item.setBusinessID(SharedPreferenceHelper.getInstance(requireContext()).getBUSINESS_ID());

                           saveCall(item);
                        }
                        else
                        {
                            mBinding.etSalePriceLayout.setError("Please Enter valid sale price");

                        }
                    }
                    else
                    {
                        mBinding.etCostPriceLayout.setError("Please Enter valid cost price");

                    }
                }
                else
                {
                    mBinding.etCartonSizeLayout.setError("Please Enter valid carton size");

                }
            }
            else
            {
                mBinding.etUnitSizeLayout.setError("Please Enter valid unit size");

            }

        } else {
            mBinding.etItemNameLayout.setError("Please enter the name.");
        }
    }

    private void saveCall(Item item) {

        stockViewModel.saveItem(item).observe(getViewLifecycleOwner(), new Observer<ServerResponse>() {
            @Override
            public void onChanged(ServerResponse serverResponse) {

                if (serverResponse!=null)
                {
                    Toast.makeText(requireContext(), ""+serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getSupplier() {

        stockViewModel.getParty().observe(getViewLifecycleOwner(), new Observer<List<Party>>() {
            @Override
            public void onChanged(List<Party> partyList) {

                if (partyList != null) {
                    setupSupplierSpinner(partyList);
                }
            }
        });
    }

    private void setupSupplierSpinner(List<Party> list) {

        for (Party model : list) {
            supplierNameList.add(model.getPartyName());
            supplierHashMapForID.put(model.getPartyName(), model.getPartyCode());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, supplierNameList);

        mBinding.supplierSpinner.setAdapter(adapter);
    }

    private void getDepartment() {

        stockViewModel.getDepartment().observe(getViewLifecycleOwner(), new Observer<List<Department>>() {
            @Override
            public void onChanged(List<Department> departments) {

                if (departments != null) {

                    setupDepartmentSpinner(departments);
                }
            }
        });
    }

    public void setupDepartmentSpinner(List<Department> list) {

        for (Department model : list) {
            departmentHashMapForID.put(model.getDepartmentName(), model.getDepartmentCode());
            departmentNameList.add(model.getDepartmentName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, departmentNameList);

        mBinding.departmentSpinner.setAdapter(adapter);
    }
}