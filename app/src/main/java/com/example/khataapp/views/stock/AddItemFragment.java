package com.example.khataapp.views.stock;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.util.StringUtil;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.khataapp.databinding.FragmentAddItemBinding;
import com.example.khataapp.databinding.ImageSelectDialogBinding;
import com.example.khataapp.models.Department;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.response.ServerResponse;
import com.example.khataapp.utils.DialogUtil;
import com.example.khataapp.utils.ImageUtil;
import com.example.khataapp.utils.Permission;
import com.example.khataapp.views.stock.viewmodel.StockViewModel;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddItemFragment extends Fragment {


    private FragmentAddItemBinding mBinding;
    private StockViewModel stockViewModel;
    private final HashMap<String, String> departmentHashMapForID = new HashMap<>();
    private final HashMap<String, String> departmentHashMapForName = new HashMap<>();
    private final HashMap<String, String> supplierHashMapForID = new HashMap<>();
    private final HashMap<String, String> supplierHashMapForName = new HashMap<>();
    private final ArrayList<String> supplierNameList = new ArrayList<>();
    private final ArrayList<String> departmentNameList = new ArrayList<>();
    private AlertDialog progressDialog;
    private Bitmap selectedImage = null;
    private boolean isEdit = false;
    private Item editedItem;
    private String saveAction = "INSERT";
    private String departmentCode = "0001", supplierCode = "", barcode = "", uan = "";
    private List<String> uanList = new ArrayList<>();


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
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());

        getDepartment();
        getSupplier();
        btnListener();
        liveDataListeners();

        if (getArguments() != null) {
            barcode = AddItemFragmentArgs.fromBundle(getArguments()).getItemCode();
            if (barcode != null && !barcode.isEmpty()) getItemByCode();
        }

    }

    private void liveDataListeners() {

        stockViewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), flag -> {
            if (flag) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });

    }

    private void getItemByCode() {
        new Handler().postDelayed(() -> stockViewModel.getItemByCode(barcode), 2000);
        stockViewModel.getItemMutableLiveData().observe(getViewLifecycleOwner(), this::setupFields);
    }

    private void setupFields(Item item) {

        editedItem = item;
        if (editedItem != null) {
            mBinding.setItem(editedItem);
            mBinding.itemImage2.setVisibility(View.GONE);
            isEdit = true;
            saveAction = "UPDATE";
            departmentCode = editedItem.getDepartmentCode();
            String departmentName = departmentHashMapForName.get(departmentCode);
            if (departmentName != null && !departmentName.isEmpty()) {
                mBinding.departmentSpinnerLayout.setVisibility(View.VISIBLE);
                mBinding.btnDepartment.setChecked(true);
                mBinding.departmentSpinner.setText(departmentName);
            } else {
                departmentCode = "0001";
            }
            supplierCode = editedItem.getSupplierCode();
            String supplierName = supplierHashMapForName.get(supplierCode);
            if (supplierName != null && !supplierName.isEmpty()) {
                mBinding.supplierSpinnerLayout.setVisibility(View.VISIBLE);
                mBinding.btnSupplier.setChecked(true);
                mBinding.supplierSpinner.setText(supplierName);
            } else {
                supplierCode = "000001";
            }

            barcode = editedItem.getBarcode();
            uan = editedItem.getUan();
            uanList = editedItem.getUanList();
        }
    }


    private void btnListener() {

        mBinding.btnDepartment.setOnClickListener(v -> {

            if (mBinding.btnDepartment.isChecked()) {
                mBinding.departmentSpinnerLayout.setVisibility(View.VISIBLE);
            } else {
                mBinding.departmentSpinnerLayout.setVisibility(View.GONE);
                mBinding.departmentSpinner.setText("");
                departmentCode = "0001";

            }
        });

        mBinding.btnSupplier.setOnClickListener(v -> {

            if (mBinding.btnSupplier.isChecked()) {
                mBinding.supplierSpinnerLayout.setVisibility(View.VISIBLE);
            } else {
                mBinding.supplierSpinnerLayout.setVisibility(View.GONE);
                mBinding.supplierSpinner.setText("");
                supplierCode = "000001";
            }
        });

        mBinding.btnSave.setOnClickListener(v -> saveItem());

        mBinding.imageName.setOnClickListener(view -> {
            showSelectImageDialog();
        });
    }

    private void saveItem() {

        progressDialog.show();
        String unitSize, cartonSize, costPrice, salePrice;

        if (mBinding.etItemName.getText() != null && !mBinding.etItemName.getText().toString().equals(".")) {

        }
        Item item = new Item();

        if (mBinding.etItemName.getText() != null && !mBinding.etItemName.getText().toString().isEmpty()) {
            mBinding.etItemNameLayout.setError(null);
            if (mBinding.etUnitSize.getText() != null && !mBinding.etUnitSize.getText().toString().equals(".")) {
                mBinding.etUnitSizeLayout.setError(null);
                if (mBinding.etCartonSize.getText() != null && !mBinding.etCartonSize.getText().toString().equals(".")) {
                    mBinding.etCartonSizeLayout.setError(null);
                    if (mBinding.etCostPrice.getText() != null && !mBinding.etCostPrice.getText().toString().equals(".")) {
                        mBinding.etCostPriceLayout.setError(null);
                        if (mBinding.etSalePrice.getText() != null && !mBinding.etSalePrice.getText().toString().equals(".")) {
                            mBinding.etSalePriceLayout.setError(null);


                            if (mBinding.etCartonSize.getText().toString().length() > 0) {

                                item.setCtnPcs(Double.parseDouble(mBinding.etCartonSize.getText().toString()));

                            }


                            if (mBinding.etUnitSize.getText().toString().length() > 0) {

                                item.setUnitSize(Double.parseDouble(mBinding.etUnitSize.getText().toString()));

                            }


                            if (mBinding.etCostPrice.getText().toString().length() > 0) {


                                item.setUnitCost(Double.parseDouble(mBinding.etCostPrice.getText().toString()));

                            }

                            if (mBinding.etSalePrice.getText().toString().length() > 0) {

                                item.setUnitRetail(Double.parseDouble(mBinding.etSalePrice.getText().toString()));
                            }
                            if (mBinding.etScheme.getText().toString().length() > 0) {

                                item.setScheme(Double.parseDouble(mBinding.etScheme.getText().toString()));
                            }
                            if (mBinding.etFreeQty.getText().toString().length() > 0) {

                                item.setFreeQty(Double.parseDouble(mBinding.etFreeQty.getText().toString()));
                            }

                            if (departmentNameList.size() != 0) {
                                String name = mBinding.departmentSpinner.getText().toString();
                                if (!name.isEmpty()) {
                                    departmentCode = departmentHashMapForID.get(name);
                                    item.setDepartmentCode(departmentCode);
                                    item.setGroupCode(departmentHashMapForID.get(name));
                                    item.setSubGroupCode(departmentHashMapForID.get(name));
                                } else {
                                    item.setDepartmentCode(departmentCode);
                                    item.setGroupCode(departmentCode);
                                    item.setSubGroupCode(departmentCode);
                                }


                            } else {
                                item.setDepartmentCode(departmentCode);
                                item.setGroupCode(departmentCode);
                                item.setSubGroupCode(departmentCode);
                            }


                            if (mBinding.supplierSpinner.getText() != null && mBinding.supplierSpinner.getText().toString().length() > 0) {
                                String name = mBinding.supplierSpinner.getText().toString();
                                supplierCode = supplierHashMapForID.get(name);
                                item.setSupplierCode(supplierCode);

                            } else {
                                item.setSupplierCode("000001");
                            }

                            item.setDescription(mBinding.etItemName.getText().toString());
                            item.setStatus("a");
                            item.setProductType("P");
                            item.setAction(saveAction);
                            item.setBarcode(barcode);
                            item.setUan(uan);
                            item.setUanList(uanList);
                            item.setUserID(SharedPreferenceHelper.getInstance(requireContext()).getUserID());
                            item.setBusinessID(SharedPreferenceHelper.getInstance(requireContext()).getBUSINESS_ID());

                            if (selectedImage != null) {
                                item.setProductImage(ImageUtil.getInstance().getBytesFromBitmap(selectedImage));

                            } else {
                                if (isEdit) {
                                    item.setProductImage(editedItem.getProductImage());

                                }
                            }

                            saveCall(item);
                        } else {
                            mBinding.etSalePriceLayout.setError("Please Enter valid sale price");

                        }
                    } else {
                        mBinding.etCostPriceLayout.setError("Please Enter valid cost price");

                    }
                } else {
                    mBinding.etCartonSizeLayout.setError("Please Enter valid carton size");

                }
            } else {
                mBinding.etUnitSizeLayout.setError("Please Enter valid unit size");

            }

        } else {
            mBinding.etItemNameLayout.setError("Please enter the name.");
        }
    }

    private void saveCall(Item item) {

        stockViewModel.saveItem(item).observe(getViewLifecycleOwner(), serverResponse -> {

            if (serverResponse != null) {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), "" + serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            supplierHashMapForName.put(model.getPartyCode(), model.getPartyName());
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
            departmentHashMapForName.put(model.getDepartmentCode(), model.getDepartmentName());
            departmentNameList.add(model.getDepartmentName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, departmentNameList);

        mBinding.departmentSpinner.setAdapter(adapter);
    }

    private void showSelectImageDialog() {
        Permission permission = new Permission(requireContext(), requireActivity());

        ImageSelectDialogBinding dialogBinding = ImageSelectDialogBinding.inflate(getLayoutInflater());
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                .setView(dialogBinding.getRoot())
                .setCancelable(false).create();
        alertDialog.show();

        dialogBinding.takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    permission.getCameraPermission();
                } else {

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (cameraIntent.resolveActivity(requireContext().getPackageManager()) != null) {
                        startActivityForResult(cameraIntent, 0);
                    }

                }
                alertDialog.dismiss();

            }
        });

        dialogBinding.selectFromGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    permission.getStoragePermission();

                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(galleryIntent, 1);
                }
                alertDialog.dismiss();


            }
        });
        dialogBinding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage = (Bitmap) data.getExtras().get("data");
                        mBinding.itemImage2.setImageBitmap(selectedImage);
                        mBinding.itemImage2.setVisibility(View.VISIBLE);
                        mBinding.itemImage.setVisibility(View.GONE);
                    }
                    break;

                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImageUri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = requireActivity().getContentResolver().query(selectedImageUri,
                                filePathColumn, null, null, null);
                        if (cursor != null) {
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            selectedImage = BitmapFactory.decodeFile(picturePath);
                            mBinding.itemImage2.setImageBitmap(selectedImage);
                            mBinding.itemImage2.setVisibility(View.VISIBLE);
                            mBinding.itemImage.setVisibility(View.GONE);
                            cursor.close();
                        }


                    }
                    break;
            }

        }
    }


}