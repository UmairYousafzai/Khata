package com.example.khataapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.khataapp.adapter.LocationListAdapter;
import com.example.khataapp.databinding.CustomAddLocationDialogBinding;
import com.example.khataapp.databinding.CustomLocationsDialogBinding;
import com.example.khataapp.databinding.FragmentSignUpBinding;
import com.example.khataapp.models.GetLocationResponse;
import com.example.khataapp.models.Location;
import com.example.khataapp.models.PostLocation;
import com.example.khataapp.models.ServerResponse;
import com.example.khataapp.models.SignUpUser;
import com.example.khataapp.network.Api;
import com.example.khataapp.network.ApiClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding mBinding;
    private NavController navController;
    private List<Location> locationList= new ArrayList<>();
    private HashMap<String,String> locationCodeHashMap=new HashMap<>();
    private boolean isPasswordSame= false;
    private String businessName="";


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentSignUpBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        getLocations();
    }

    @Override
    public void onResume() {
        super.onResume();

        btnListener();
        textWacther();

    }

    private void textWacther() {

        mBinding.etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mBinding.etPassword.getText()!=null)
                {
                    String password =mBinding.etPassword.getText().toString();

                    if (password.equals(editable.toString()))
                    {
                        isPasswordSame= true;
                        mBinding.etConfirmPasswordLayout.setError(null);

                    }
                    else
                    {
                        isPasswordSame=false;
                        mBinding.etConfirmPasswordLayout.setError("Please enter valid password");
                    }
                }

            }
        });
    }

    private void btnListener() {
        mBinding.locationSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocationDialog();
            }
        });

        mBinding.btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddLocationDialog();
            }
        });

        mBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isPasswordSame)
                {
                    SaveUser();
                }
                else
                {
                    Toast.makeText(requireContext(), "Please enter valid password", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void SaveUser() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Saving....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SignUpUser signUpUser = new SignUpUser();

        String username="",phoneNumber="",email="",address="",password="",locationCode="";
        if (mBinding.etUserName.getText()!=null)
        {
            username= mBinding.etUserName.getText().toString();
            signUpUser.setUserName(username);
        }
             if (mBinding.etPhoneNumber.getText()!=null)
        {
            phoneNumber= mBinding.etPhoneNumber.getText().toString();
            signUpUser.setMobile(phoneNumber);

        }
             if (mBinding.etEmail.getText()!=null)
        {
            email= mBinding.etEmail.getText().toString();
            signUpUser.setEmail(email);

        }
             if (mBinding.etAddress.getText()!=null)
        {
            address= mBinding.etAddress.getText().toString();
            signUpUser.setAddress(address);

        }
             if (mBinding.etBusinessName.getText()!=null)
        {
            businessName= mBinding.etBusinessName.getText().toString();
            signUpUser.setBusiness(businessName);

        }
             if (mBinding.etPassword.getText()!=null)
        {
            password= mBinding.etPassword.getText().toString();
            signUpUser.setPassword(password);

        }
             if (mBinding.locationSpinner.getText()!=null)
        {

            locationCode= locationCodeHashMap.get(mBinding.locationSpinner.getText().toString());
            signUpUser.setLocationCode(locationCode);

        }


             Call<ServerResponse> call = ApiClient.getInstance().getApi().saveUser(signUpUser);
             call.enqueue(new Callback<ServerResponse>() {
                 @Override
                 public void onResponse(@NotNull Call<ServerResponse> call, @NotNull Response<ServerResponse> response) {

                     if (response.isSuccessful())
                     {
                         if (response.body()!=null)
                         {
                             ServerResponse serverResponse = response.body();
                             Toast.makeText(requireContext(), ""+serverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                             SignUpFragmentDirections.ActionSignUpFragmentToHomeFragment action = SignUpFragmentDirections
                                     .actionSignUpFragmentToHomeFragment();
                             action.setBusinessName(businessName);

                             navController.navigate(action);
                         }
                         else
                         {
                             Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                         }

                     }
                     else
                     {
                         Toast.makeText(requireContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                     }
                     progressDialog.dismiss();

                 }

                 @Override
                 public void onFailure(@NotNull Call<ServerResponse> call, @NotNull Throwable t) {
                     progressDialog.dismiss();
                     Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             });




    }

    private void showAddLocationDialog() {

        CustomAddLocationDialogBinding binding = CustomAddLocationDialogBinding.inflate(getLayoutInflater());

        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                .setView(binding.getRoot())
                .create();
        alertDialog.show();

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (binding.etLocation.getText()!=null)
                {
                    PostLocation postLocation =new PostLocation();
                    postLocation.setName(binding.etLocation.getText().toString());
                    saveLocation(postLocation);
                    alertDialog.dismiss();
                }
                else
                {
                    binding.etLocationLayout.setError("Please enter name");
                    Toast.makeText(requireContext(), "Please enter name", Toast.LENGTH_SHORT).show();
                }





            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });
    }

    private void saveLocation(PostLocation postLocation) {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Saving....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<ServerResponse> call = ApiClient.getInstance().getApi().saveLocation(postLocation);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NotNull Call<ServerResponse> call, @NotNull Response<ServerResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        ServerResponse serverResponse = response.body();
                        Toast.makeText(requireContext(), ""+serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        getLocations();
                    }
                    else
                    {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(requireContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(@NotNull Call<ServerResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void showLocationDialog() {

        CustomLocationsDialogBinding binding = CustomLocationsDialogBinding.inflate(getLayoutInflater());

        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                .setView(binding.getRoot())
                .create();
        alertDialog.show();

        binding.locationRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        LocationListAdapter adapter= new LocationListAdapter();
        binding.locationRecycler.setAdapter(adapter);



        adapter.setLocationList(locationList);

        adapter.SetOnClickListener(new LocationListAdapter.SetOnClickListener() {
            @Override
            public void onClick(Location location) {

                if(location!=null)
                {
                    if (location.getName()!=null)
                    {
                        mBinding.locationSpinner.setText(location.getName());

                    }
                }

                alertDialog.dismiss();
            }
        });
    }


    public void getLocations()
    {
        ProgressDialog progressDialog= new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading .....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<GetLocationResponse> call = ApiClient.getInstance().getApi().getLocations();

        call.enqueue(new Callback<GetLocationResponse>() {
            @Override
            public void onResponse(@NotNull Call<GetLocationResponse> call, @NotNull Response<GetLocationResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        GetLocationResponse getLocationResponse= response.body();
                        if (getLocationResponse.getData().size()>0)
                        {
                            locationList= getLocationResponse.getData();
                            for (Location model:locationList)
                            {
                                locationCodeHashMap.put(model.getName(),model.getCode());
                            }
                        }
                        else
                        {
                            Toast.makeText(requireContext(), "Locations not found", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else
                    {
                        Toast.makeText(requireContext(), "Locations not found", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(requireContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(@NotNull Call<GetLocationResponse> call, @NotNull Throwable t) {

                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }
}