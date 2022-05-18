package com.example.khataapp.views.authantication;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

import com.example.khataapp.R;
import com.example.khataapp.adapter.LocationListAdapter;
import com.example.khataapp.databinding.CustomAddLocationDialogBinding;
import com.example.khataapp.databinding.CustomLocationsDialogBinding;
import com.example.khataapp.databinding.FragmentSignUpBinding;
import com.example.khataapp.models.GetLocationResponse;
import com.example.khataapp.models.GetPartiesServerResponse;
import com.example.khataapp.models.Location;
import com.example.khataapp.models.LoginResponse;
import com.example.khataapp.models.PostLocation;
import com.example.khataapp.models.response.ServerResponse;
import com.example.khataapp.models.SignUpUser;
import com.example.khataapp.network.ApiClient;
import com.example.khataapp.utils.DataViewModel;
import com.example.khataapp.utils.DialogUtil;
import com.example.khataapp.utils.SharedPreferenceHelper;

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
    private DataViewModel dataViewModel;
    private AlertDialog progressDialog;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentSignUpBinding.inflate(inflater,container,false);



        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());


        navController = NavHostFragment.findNavController(this);
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        getLocations();
    }

    @Override
    public void onResume() {
        super.onResume();

        btnListener();
        textWatcher();

    }

    private void textWatcher() {

        mBinding.etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mBinding.etConfirmPasswordLayout.setError(null);
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

                SaveUser();

//                if (isPasswordSame)
//                {
//                    SaveUser();
//                }
//                else
//                {
//                    Toast.makeText(requireContext(), "Please enter valid password", Toast.LENGTH_SHORT).show();
//                }

            }
        });

        mBinding.tvAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment();
                navController.navigate(navDirections);
            }
        });

    }

    public boolean validateData()
    {
        boolean check = true;

        if (mBinding.etUserName.getText()!=null && mBinding.etUserName.getText().toString().isEmpty())
        {
            mBinding.etUserNameLayout.setError("Please Enter UserName");
            mBinding.etUserName.requestFocus();
            check= false;
        }    if (mBinding.etPhoneNumber.getText()!=null && mBinding.etPhoneNumber.getText().toString().isEmpty())
        {
            mBinding.etPhoneNumberLayout.setError("Please Enter Phone Number");
            mBinding.etPhoneNumber.requestFocus();
            check= false;
        }   if (mBinding.etEmail.getText()!=null && mBinding.etEmail.getText().toString().isEmpty())
        {
            mBinding.etEmailLayout.setError("Please Enter Email");
            mBinding.etEmail.requestFocus();
            check= false;
        } if (mBinding.etBusinessName.getText()!=null && mBinding.etBusinessName.getText().toString().isEmpty())
        {
            mBinding.etBusinessNameLayout.setError("Please Enter Business Name");
            mBinding.etBusinessName.requestFocus();
            check= false;
        }if (mBinding.locationSpinner.getText()!=null && mBinding.locationSpinner.getText().toString().isEmpty())
        {
            Toast.makeText(requireContext(), "Please Select Location", Toast.LENGTH_SHORT).show();
            check= false;
        }if  (!mBinding.etPassword.getText().toString().equals( mBinding.etConfirmPassword.getText().toString()))
        {
            mBinding.etConfirmPasswordLayout.setError("Please Type correct Reconfirm  Password");
            mBinding.etConfirmPassword.requestFocus();
            check= false;
        }


        return check;
    }
    private void SaveUser() {

        if (validateData())
        {

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
                signUpUser.setBusiness(mBinding.etBusinessName.getText().toString());

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


            Call<LoginResponse> call = ApiClient.getInstance().getApi().saveUser(signUpUser);
            String finalPassword = password;
            String finalUsername = username;
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {

                    if (response.isSuccessful())
                    {
                        if (response.body()!=null)
                        {
                            LoginResponse loginResponse = response.body();
                            if (loginResponse.getCode()==200)
                            {
                                SharedPreferenceHelper.getInstance(requireContext()).setUserID(loginResponse.getUser().getUserId());
                                SharedPreferenceHelper.getInstance(requireContext()).setBUSINESS_ID(loginResponse.getUser().getBusinessId());
                                SharedPreferenceHelper.getInstance(requireContext()).setIsLogin(true);
                                dataViewModel.insertUser(loginResponse.getUser());
                                login(finalUsername, finalPassword);
                                //                             navController.navigate(R.id.action_signUpFragment_to_homeFragment);

                            }
                            Toast.makeText(requireContext(), ""+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();



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
                public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }




    }

    private void getUser() {


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


    private void login(String username, String password) {
        progressDialog.show();

        Call<LoginResponse> call = ApiClient.getInstance().getApi().login(username,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        LoginResponse loginResponse= response.body();

                        if (loginResponse.getCode() == 200) {

                            SharedPreferenceHelper.getInstance(requireContext()).setUserID(loginResponse.getUser().getUserId());
                            SharedPreferenceHelper.getInstance(requireContext()).setBUSINESS_ID(loginResponse.getUser().getBusinessId());
                            SharedPreferenceHelper.getInstance(requireContext()).setIsLogin(true);
                            SharedPreferenceHelper.getInstance(requireContext()).setBusinessName(loginResponse.getUser().getBusinessName());

                            dataViewModel.insertUser(loginResponse.getUser());
                            Toast.makeText(requireContext(), "" + loginResponse.getUser().getUserName(), Toast.LENGTH_LONG).show();

                            progressDialog.show();
                            getParties("c");
                        }
                        else if(loginResponse.getCode()==401)
                        {
                            mBinding.etUserNameLayout.setError("Invalid");
                            mBinding.etPasswordLayout.setError("Invalid");
                            mBinding.etUserName.requestFocus();
                        }

                        Toast.makeText(requireContext(), ""+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(requireContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }

    public String getParties(String type) {



        String businessID= SharedPreferenceHelper.getInstance(requireContext()).getBUSINESS_ID();

        Call<GetPartiesServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID,type);
        call.enqueue(new Callback<GetPartiesServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPartiesServerResponse> call, @NonNull Response<GetPartiesServerResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        GetPartiesServerResponse getPartiesServerResponse = response.body();

                        if (getPartiesServerResponse.getCode()==200)
                        {

                            if (getPartiesServerResponse.getPartyList()!=null&& getPartiesServerResponse.getPartyList().size()>0)
                            {

                                dataViewModel.insertParties(getPartiesServerResponse.getPartyList());


                            }
                        }

                    }

                }
                else
                {
                    if (response.errorBody() != null) {
                        Toast.makeText(requireContext(), ""+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressDialog.dismiss();
                if (type.equals("s"))
                {
                    progressDialog.dismiss();
                    navController.navigate(R.id.action_signUpFragment_to_homeFragment);

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPartiesServerResponse> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if (type.equals("s"))
                {
                    progressDialog.dismiss();
                    navController.navigate(R.id.action_signUpFragment_to_homeFragment);

                }

            }
        });

        if (type.equals("c"))
        {
            return getParties("s");
        }
        else
        {
            return "break";
        }

    }
}