package com.example.khataapp.views;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khataapp.BuildConfig;
import com.example.khataapp.databinding.FragmentSplashScreenBinding;
import com.example.khataapp.utils.DataViewModel;
import com.example.khataapp.utils.SharedPreferenceHelper;


public class SplashScreenFragment extends Fragment {

    private FragmentSplashScreenBinding mBinding;
    private NavController navController;
    private DataViewModel dataViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentSplashScreenBinding.inflate(inflater,container,false);

//        MeowBottomNavigation navBar = requireActivity().findViewById(R.id.bottom_view);
//
//        if (navBar!=null)
//        {
//            navBar.setVisibility(View.GONE);
//        }


        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        dataViewModel= new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.deleteParties();

        mBinding.versionName.setText(String.format("Version %s", BuildConfig.VERSION_NAME));



        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                requireActivity().finishAffinity();
                        }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
//        if (((AppCompatActivity) requireActivity()).getSupportActionBar()!=null)
//        {
//            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
//
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLogin();
                dataViewModel.getAndSaveParties();
            }
        },500);

    }
//
//    public String getParties(String type) {
//
//
//        String businessID= SharedPreferenceHelper.getInstance(requireContext()).getBUSINESS_ID();
//
//        Call<GetPartyServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID,type);
//        call.enqueue(new Callback<GetPartyServerResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<GetPartyServerResponse> call, @NonNull Response<GetPartyServerResponse> response) {
//
//                if (response.isSuccessful())
//                {
//                    if (response.body()!=null)
//                    {
//                        GetPartyServerResponse getPartyServerResponse= response.body();
//
//                        if (getPartyServerResponse.getCode()==200)
//                        {
//
//                            if (getPartyServerResponse.getPartyList()!=null&& getPartyServerResponse.getPartyList().size()>0)
//                            {
//
//                                dataViewModel.insertParties(getPartyServerResponse.getPartyList());
//
//                            }
//                        }
//
//                    }
//
//                }
//                else
//                {
//                    if (response.errorBody() != null) {
//                        Toast.makeText(requireContext(), ""+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                if (type.equals("s"))
//                {
//                    checkLogin();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<GetPartyServerResponse> call, @NonNull Throwable t) {
//                if (type.equals("s"))
//                {
//                    checkLogin();
//                }
//            }
//        });
//
//        if (type.equals("c"))
//        {
//            return getParties("s");
//        }
//        else
//        {
//            return "break";
//        }
//
//    }

    public void checkLogin()
    {

        boolean isLogin= SharedPreferenceHelper.getInstance(requireContext()).getIS_Login();
        NavDirections navDirections;
        if (isLogin)
        {
            navDirections = SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment();

        }
        else
        {
            navDirections = SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment();

        }
        navController.navigate(navDirections);
    }
}