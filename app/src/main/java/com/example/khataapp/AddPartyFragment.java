package com.example.khataapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.khataapp.databinding.FragmentAddPartyBinding;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.ServerResponse;
import com.example.khataapp.models.User;
import com.example.khataapp.network.ApiClient;
import com.example.khataapp.utils.DataViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPartyFragment extends Fragment {


    private FragmentAddPartyBinding mBinding;
    private String partyType;
    private DataViewModel dataViewModel;
    private NavController navController;
    private User user= new User();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentAddPartyBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController= NavHostFragment.findNavController(this);
        dataViewModel= new ViewModelProvider(this).get(DataViewModel.class);


        if (getArguments()!=null)
        {
            partyType= AddPartyFragmentArgs.fromBundle(getArguments()).getPartyType();
        }

        getUserLiveData();
    }

    private void getUserLiveData() {

        dataViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if (users!=null&& users.size()>0)
                {
                    user= users.get(0);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        btnListener();
    }

    private void btnListener() {

        mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.etPartyName.getText()!=null&& mBinding.etPartyName.getText().toString().length()>0)
                {
                    saveParty();
                }
                else
                {
                    mBinding.etPartyNameLayout.setError("please enter party name");
                    mBinding.etPartyNameLayout.requestFocus();
                }

            }
        });
    }

    private void saveParty() {

        Party party = new Party();
        party.setPartyName(mBinding.etPartyName.getText().toString());
        party.setPartyType(partyType);

        if (mBinding.etPartyAddress.getText()!=null&& mBinding.etPartyAddress.getText().toString().length()>0)
        {
            party.setPartyAddress(mBinding.etPartyAddress.getText().toString());

        }

        if (mBinding.etEmail.getText()!=null&& mBinding.etEmail.getText().toString().length()>0)
        {
            party.setEmail(mBinding.etEmail.getText().toString());
        }
        if (mBinding.etMobileNumber.getText()!=null&& mBinding.etMobileNumber.getText().toString().length()>0)
        {
            party.setMobile(mBinding.etMobileNumber.getText().toString());
        }
        if (mBinding.etPhoneNumber.getText()!=null&& mBinding.etPhoneNumber.getText().toString().length()>0)
        {
            party.setPhone(mBinding.etPhoneNumber.getText().toString());
        }


        party.setAction("INSERT");

        if (user!=null)
        {
            if (user.getUserId()!=null&&user.getUserId().length()>0)
            {
                party.setUserId(user.getUserId());
            }
            if (user.getBusinessId()!=null&& user.getBusinessId().length()>0)
            {
                party.setBusinessId(user.getBusinessId());
            }

        }
        if (mBinding.etCNIC.getText()!=null&& mBinding.etCNIC.getText().toString().length()<14)
        {
            party.setCnic(mBinding.etCNIC.getText().toString());
            generateSaveRequest(party);

        }
        else
        {
            mBinding.etCNICLayout.setError("CNIC max length is 13");
            mBinding.etCNIC.requestFocus();
        }
    }

    private void generateSaveRequest(Party party) {

        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Party");
        progressDialog.setMessage("Saving....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<ServerResponse> call = ApiClient.getInstance().getApi().saveParty(party);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        ServerResponse serverResponse= response.body();
                        Toast.makeText(requireContext(), ""+serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (serverResponse.getCode()==200)
                        {
                            navController.popBackStack();
                        }

                    }

                }
                else
                {
                    Toast.makeText(requireContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}