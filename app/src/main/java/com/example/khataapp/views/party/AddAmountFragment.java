package com.example.khataapp.views.party;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.khataapp.R;
import com.example.khataapp.databinding.FragmentAddAmountBinding;
import com.example.khataapp.databinding.ImageSelectDialogBinding;
import com.example.khataapp.models.Party;
import com.example.khataapp.utils.DateUtil;
import com.example.khataapp.utils.DialogUtil;
import com.example.khataapp.utils.ImageUtil;
import com.example.khataapp.utils.Permission;
import com.example.khataapp.viewModel.AddAmountViewModel;
import com.example.khataapp.viewModel.BaseViewModel;

import java.util.Calendar;

public class AddAmountFragment extends Fragment {

    private FragmentAddAmountBinding mBinding;
    private AddAmountViewModel viewModel;
    private boolean imageSelected = false;
    private AlertDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAddAmountBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());


        viewModel = new ViewModelProvider(this).get(AddAmountViewModel.class);

        mBinding.setViewModel(viewModel);
        mBinding.tvAmountDetail.setMovementMethod(new ScrollingMovementMethod());
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        if (getArguments() != null) {
            Party party = AddAmountFragmentArgs.fromBundle(getArguments()).getParty();
            setUI(party);
            if (party.getPartyType().equals("c")) {
                viewModel.setVoucherType("CR");
            } else {
                viewModel.setVoucherType("CP");

            }
            viewModel.setPartyCode(party.getPartyCode());
        }

        setUpBaseViewModel(viewModel);
        btnListener();
        editTextFocusListener();
        textWatcher();

    }

    private void setUI(Party party) {

        if (party.getPartyType().equals("s"))
        {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Supplier Voucher");
        }
        else
        {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Customer Voucher");

        }

        mBinding.btnDate.setText(DateUtil.getInstance().getDate());
    }

    private void textWatcher() {

        mBinding.etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mBinding.etAmount.setSelection(mBinding.etAmount.getText().length());

            }
        });
    }

    private void editTextFocusListener() {
        mBinding.etAmount.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down);
                mBinding.keyboardLayout.GlKeyboard.setAnimation(animation);
                mBinding.keyboardLayout.GlKeyboard.setVisibility(View.GONE);            }
        });
    }

    private void setUpBaseViewModel(BaseViewModel baseViewModel) {
        baseViewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        baseViewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean flag) {
                if (flag) progressDialog.show();
                else progressDialog.dismiss();
            }
        });
    }

    private void btnListener() {


        mBinding.etAmount.setOnClickListener(view -> {
            closeKeyBoard();
            showAndHideCustomKeyBoard();
        });


        mBinding.tvAddBillNo.setOnClickListener(view -> {
            if (mBinding.etBillNum.getVisibility() == View.VISIBLE)
                mBinding.etBillNum.setVisibility(View.GONE);
            else mBinding.etBillNum.setVisibility(View.VISIBLE);

        });

        mBinding.btnAttachBill.setOnClickListener(view -> {
            if (!imageSelected) showSelectImageDialog();

        });

        mBinding.btnSave.setOnClickListener(view -> {
            viewModel.saveVoucher();
        });

    }

    private void showAndHideCustomKeyBoard() {

        if (mBinding.keyboardLayout.GlKeyboard.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up);
            mBinding.keyboardLayout.GlKeyboard.setAnimation(animation);
            mBinding.keyboardLayout.GlKeyboard.setVisibility(View.VISIBLE);
            closeKeyBoard();

        } else {
            Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down);
            mBinding.keyboardLayout.GlKeyboard.setAnimation(animation);
            mBinding.keyboardLayout.GlKeyboard.setVisibility(View.GONE);


        }
    }


    public void closeKeyBoard() {
        View view = requireActivity().getCurrentFocus();

        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }


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
            Bitmap selectedImage;
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        imageSelected = true;
                        mBinding.btnAttachBill.setVisibility(View.GONE);
                        mBinding.imageLayout.setVisibility(View.VISIBLE);
                        selectedImage = (Bitmap) data.getExtras().get("data");
                        mBinding.billImageView.setImageBitmap(selectedImage);
                        viewModel.setBillImage(ImageUtil.getInstance().getBytesFromBitmap(selectedImage));
                    }
                    break;

                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        imageSelected = true;
                        mBinding.btnAttachBill.setVisibility(View.GONE);
                        mBinding.imageLayout.setVisibility(View.VISIBLE);
                        Uri selectedImageUri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = requireActivity().getContentResolver().query(selectedImageUri,
                                filePathColumn, null, null, null);
                        if (cursor != null) {
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            selectedImage = BitmapFactory.decodeFile(picturePath);
                            mBinding.billImageView.setImageBitmap(selectedImage);
                            viewModel.setBillImage(ImageUtil.getInstance().getBytesFromBitmap(selectedImage));

                            cursor.close();
                        }


                    }
                    break;
            }

        }
    }

    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            if (mBinding.keyboardLayout.GlKeyboard.getVisibility() == View.VISIBLE) {
                showAndHideCustomKeyBoard();
            } else {
                NavHostFragment.findNavController(AddAmountFragment.this).popBackStack();
            }
        }
    };



}