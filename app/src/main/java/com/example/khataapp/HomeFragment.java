package com.example.khataapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.khataapp.adapter.PartyListAdapter;
import com.example.khataapp.databinding.FragmentHomeBinding;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.User;
import com.example.khataapp.utils.CONSTANTS;
import com.example.khataapp.utils.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private final int CUSTOMER_FRAGMENT = 1, SUPPLIER_FRAGMENT = 2, ALL_FRAGMENT = 3;
    private FragmentHomeBinding mBinding;
    private NavController navController;
    private String businessName;
    private NavBackStackEntry navBackStackEntry;
    private DataViewModel dataViewModel;
    private LifecycleEventObserver observer;
    private User user = new User();
    private boolean isCustomer = true, isSupplier = false;
    private List<Party> partyList = new ArrayList<>();
    private PartyListAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        change_view(CUSTOMER_FRAGMENT);
//        MeowBottomNavigation navBar = requireActivity().findViewById(R.id.bottom_view);
//        navBar.setVisibility(View.VISIBLE);
//        navBar.show(1, true);
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);


        navController = NavHostFragment.findNavController(this);
        navBackStackEntry = navController.getBackStackEntry(R.id.homeFragment);
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);


        getDataFromDialog();
        getUserLiveData();
        setupRecyclerView();
    }


    @Override
    public void onResume() {
        super.onResume();
        btnListener();

        if (isCustomer) {
            getCustomerLiveData();
        } else if (isSupplier) {
            getSupplierLiveData();

        }

    }

    private void setupRecyclerView() {

        mBinding.partyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PartyListAdapter();
        mBinding.partyRecyclerView.setAdapter(adapter);
    }


    private void getCustomerLiveData() {

        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Customer");
        progressDialog.show();
        dataViewModel.getParties("c").observe(getViewLifecycleOwner(), new Observer<List<Party>>() {
            @Override
            public void onChanged(List<Party> list) {

                if (list != null && list.size() > 0) {
                    partyList.clear();
                    partyList.addAll(list);
                    adapter.setPartyList(partyList);
                    mBinding.partyRecyclerView.setVisibility(View.VISIBLE);
                    mBinding.tvFooter.setVisibility(View.GONE);
                    mBinding.imageViewAddParty.setVisibility(View.GONE);
                } else {
                    mBinding.partyRecyclerView.setVisibility(View.GONE);
                    mBinding.tvFooter.setVisibility(View.VISIBLE);
                    mBinding.imageViewAddParty.setVisibility(View.VISIBLE);
                }
                progressDialog.dismiss();
            }
        });
    }

    private void getSupplierLiveData() {
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Supplier");
        progressDialog.show();

        dataViewModel.getParties("s").observe(getViewLifecycleOwner(), new Observer<List<Party>>() {
            @Override
            public void onChanged(List<Party> list) {
                if (list != null && list.size() > 0) {
                    partyList.clear();
                    partyList.addAll(list);
                    adapter.setPartyList(partyList);
                    mBinding.partyRecyclerView.setVisibility(View.VISIBLE);
                    mBinding.tvFooter.setVisibility(View.GONE);
                    mBinding.imageViewAddParty.setVisibility(View.GONE);
                } else {
                    mBinding.partyRecyclerView.setVisibility(View.GONE);
                    mBinding.tvFooter.setVisibility(View.VISIBLE);
                    mBinding.imageViewAddParty.setVisibility(View.VISIBLE);
                }
                progressDialog.dismiss();

            }

        });
    }

    private void getDataFromDialog() {


        observer = (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_RESUME) && navBackStackEntry.getSavedStateHandle().contains(CONSTANTS.BUSINESS_NAME)) {

                businessName = navBackStackEntry.getSavedStateHandle().get(CONSTANTS.BUSINESS_NAME);
//                mBinding.etBusinessName.setText(businessName);

            }
        };
        navBackStackEntry.getLifecycle().addObserver(observer);

        // As addObserver() does not automatically remove the observer, we
        // call removeObserver() manually when the view lifecycle is destroyed
        getViewLifecycleOwner().getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                    navBackStackEntry.getLifecycle().removeObserver(observer);

                }
            }
        });
    }

    private void getUserLiveData() {

        dataViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if (users != null && users.size() > 0) {
                    user = users.get(0);
                    mBinding.etBusinessName.setText(user.getBusinessName());
                }
            }
        });

    }


    private void btnListener() {
        mBinding.btnSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToSearchFilterBottomSheetFragment();
                navController.navigate(navDirections);
            }
        });
        mBinding.ImageButtonCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToCalendarFragment();
                navController.navigate(navDirections);
            }
        });

        mBinding.etBusinessName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToEditBusinessNameBottomSheetFragment();
                navController.navigate(navDirections);
            }
        });

        mBinding.btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_view(CUSTOMER_FRAGMENT);
                getCustomerLiveData();
            }
        });
        mBinding.btnSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_view(SUPPLIER_FRAGMENT);
                getSupplierLiveData();
            }
        });
        mBinding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCustomerLiveData();
                change_view(ALL_FRAGMENT);
            }
        });

        mBinding.btnAddParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToAddPartyFragment action =
                        HomeFragmentDirections.actionHomeFragmentToAddPartyFragment();
                if (isCustomer) {

                    action.setPartyType("c");
                } else {
                    action.setPartyType("s");

                }

                navController.navigate(action);

            }
        });

        adapter.SetOnClickListener(new PartyListAdapter.SetOnClickListener() {
            @Override
            public void onClick(Party party) {

                if (party!=null)
                {
                    HomeFragmentDirections.ActionHomeFragmentToAddPartyFragment action =
                            HomeFragmentDirections.actionHomeFragmentToAddPartyFragment();
                    action.setParty(party);
                    navController.navigate(action);

                }

            }
        });

    }


    private void change_view(int i) {
        if (i == CUSTOMER_FRAGMENT) {
            mBinding.viewCustomer.setVisibility(View.VISIBLE);
            mBinding.viewSupplierIndicator.setVisibility(View.GONE);
            mBinding.viewAllIndicator.setVisibility(View.GONE);
            mBinding.customerCard.setVisibility(View.VISIBLE);
            mBinding.supplierCard.setVisibility(View.GONE);
            mBinding.cardViewMakeInvoice.setVisibility(View.GONE);
            mBinding.btnAddParty.setText("Add Customer");

            isCustomer = true;
            isSupplier = false;

        } else if (i == SUPPLIER_FRAGMENT) {
            mBinding.viewCustomer.setVisibility(View.GONE);
            mBinding.viewSupplierIndicator.setVisibility(View.VISIBLE);
            mBinding.viewAllIndicator.setVisibility(View.GONE);
            mBinding.customerCard.setVisibility(View.GONE);
            mBinding.supplierCard.setVisibility(View.VISIBLE);
            mBinding.cardViewMakeInvoice.setVisibility(View.GONE);
            mBinding.btnAddParty.setText("Add Supplier");

            isCustomer = false;
            isSupplier = true;

        } else if (i == ALL_FRAGMENT) {
            mBinding.viewCustomer.setVisibility(View.GONE);
            mBinding.viewSupplierIndicator.setVisibility(View.GONE);
            mBinding.viewAllIndicator.setVisibility(View.VISIBLE);
            mBinding.customerCard.setVisibility(View.VISIBLE);
            mBinding.supplierCard.setVisibility(View.GONE);
            mBinding.cardViewMakeInvoice.setVisibility(View.VISIBLE);
            mBinding.btnAddParty.setText("Add Customer");

            isCustomer = true;
            isSupplier = false;
        }
    }
}