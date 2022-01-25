package com.example.khataapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.khataapp.databinding.FragmentLoginBinding;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.LoginResponse;
import com.example.khataapp.network.ApiClient;
import com.example.khataapp.utils.DataViewModel;
import com.example.khataapp.utils.SharedPreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding mBinding;
    private NavController navController;
    private DataViewModel dataViewModel;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentLoginBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController= NavHostFragment.findNavController(this);
        dataViewModel= new ViewModelProvider(this).get(DataViewModel.class);
        progressDialog = new ProgressDialog(requireContext());

        btnListener();


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                requireActivity().finishAffinity();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        if (((AppCompatActivity) requireActivity()).getSupportActionBar()!=null)
        {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();

        }
    }

    private void btnListener() {

        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password,username;
                if (mBinding.etUserName.getText()!=null&&mBinding.etUserName.getText().toString().length()>0)
                {
                    if (mBinding.etPassword.getText()!=null&&mBinding.etPassword.getText().toString().length()>0)
                    {
                        password= mBinding.etPassword.getText().toString();
                        username = mBinding.etUserName.getText().toString();
                        login(username,password);

                    }
                    else
                    {
                        mBinding.etPasswordLayout.setError("Please enter Password");
                        Toast.makeText(requireContext(), "Please enter Password", Toast.LENGTH_SHORT).show();

                    }

                }
                else
                {
                    mBinding.etUserNameLayout.setError("Please enter username");

                    Toast.makeText(requireContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();

                navController.navigate(navDirections);

            }
        });
    }

    private void login(String username, String password) {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Login");
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

                            dataViewModel.insertUser(loginResponse.getUser());
                            Toast.makeText(requireContext(), "" + loginResponse.getUser().getUserName(), Toast.LENGTH_LONG).show();
                            progressDialog.setMessage("Syncing...");
                            progressDialog.setTitle("Parties");
                            progressDialog.show();
                            getParties("c");
                        }
                        else if(loginResponse.getCode()==401)
                        {
                            mBinding.etUserNameLayout.setError("Invalid");
                            mBinding.etPasswordLayout.setError("incalid");
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

        Call<GetPartyServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID,type);
        call.enqueue(new Callback<GetPartyServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPartyServerResponse> call, @NonNull Response<GetPartyServerResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        GetPartyServerResponse getPartyServerResponse= response.body();

                        if (getPartyServerResponse.getCode()==200)
                        {

                            if (getPartyServerResponse.getPartyList()!=null&& getPartyServerResponse.getPartyList().size()>0)
                            {

                                dataViewModel.insertParties(getPartyServerResponse.getPartyList());


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
                    navController.navigate(R.id.action_loginFragment_to_homeFragment);

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPartyServerResponse> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if (type.equals("s"))
                {
                    progressDialog.dismiss();
                    navController.navigate(R.id.action_loginFragment_to_homeFragment);

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