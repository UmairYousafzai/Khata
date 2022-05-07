package com.example.khataapp.views.party;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.khataapp.databinding.FragmentAddPartyBinding;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.response.ServerResponse;
import com.example.khataapp.models.User;
import com.example.khataapp.models.response.party.SavePartyResponse;
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
    private String action = "INSERT",partyCode="";
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentAddPartyBinding.inflate(inflater,container,false);

//        MeowBottomNavigation navBar = requireActivity().findViewById(R.id.bottom_view);
//        if (navBar!=null)
//        {
//            navBar.setVisibility(View.GONE);
//        }


        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController= NavHostFragment.findNavController(this);
        dataViewModel= new ViewModelProvider(this).get(DataViewModel.class);
        progressDialog= new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);


        if (getArguments()!=null)
        {
            partyType= AddPartyFragmentArgs.fromBundle(getArguments()).getPartyType();

            if (partyType.equals("c"))
            {
                if (((AppCompatActivity) requireActivity()).getSupportActionBar()!=null)
                {
                    ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Add Customer");

                }
            }
            else
            {
                if (((AppCompatActivity) requireActivity()).getSupportActionBar()!=null)
                {
                    ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Add Supplier");

                }
            }
        }

        if (getArguments()!=null)
        {
            Party party = AddPartyFragmentArgs.fromBundle(getArguments()).getParty();

            if (party!=null)
            {

                if (party.getPartyType().equals("c"))
                {
                    if (((AppCompatActivity) requireActivity()).getSupportActionBar()!=null)
                    {
                        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Add Customer");

                    }
                }
                else
                {
                    if (((AppCompatActivity) requireActivity()).getSupportActionBar()!=null)
                    {
                        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Add Supplier");

                    }
                }
                setupFields(party);

            }
        }

        getUserLiveData();
    }

    private void setupFields(Party party) {

        if (party.getPartyAddress()!=null&&party.getPartyAddress().length()>0) {
            mBinding.etPartyAddress.setText(party.getPartyAddress());
        }
        if (party.getCnic()!=null && party.getCnic().length()>0)
        {
            mBinding.etCNIC.setText(party.getCnic());
        }

        if (party.getEmail()!=null && party.getEmail().length()>0)
        {
            mBinding.etEmail.setText(party.getEmail());
        }

        if (party.getPhone()!=null && party.getPhone().length()>0)
        {
            mBinding.etPhoneNumber.setText(party.getPhone());
        }

        if (party.getMobile()!=null && party.getMobile().length()>0)
        {
            mBinding.etMobileNumber.setText(party.getMobile());
        }

        if (party.getPartyName()!=null && party.getPartyName().length()>0)
        {
            mBinding.etPartyName.setText(party.getPartyName());
        }

        action="UPDATE";
        if (party.getPartyType()!=null&& party.getPartyType().length()>0)
        {
            partyType= party.getPartyType();

        }
        if (party.getPartyCode()!=null&& party.getPartyCode().length()>0)
        {
            partyCode=party.getPartyCode();
        }


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
        textWatcher();
    }

    private void textWatcher() {

        mBinding.etCNIC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length()==13)
                {
                    mBinding.etCNICLayout.setError(null);

                }
                else {
                    mBinding.etCNICLayout.setError("CNIC Consist of 13 digit");

                }

            }
        });
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
        party.setPartyCode(partyCode);

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


        party.setAction(action);

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

        progressDialog.setTitle("Party");
        progressDialog.setMessage("Saving....");
        progressDialog.show();

        Call<SavePartyResponse> call = ApiClient.getInstance().getApi().saveParty(party);

        call.enqueue(new Callback<SavePartyResponse>() {
            @Override
            public void onResponse(@NonNull Call<SavePartyResponse> call, @NonNull Response<SavePartyResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        SavePartyResponse serverResponse= response.body();
                        Toast.makeText(requireContext(), ""+serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (serverResponse.getCode()==200)
                        {
                            dataViewModel.insertParty(serverResponse.getParty());
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
            public void onFailure(@NonNull Call<SavePartyResponse> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}