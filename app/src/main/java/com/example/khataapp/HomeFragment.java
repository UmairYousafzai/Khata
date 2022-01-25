package com.example.khataapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.khataapp.adapter.PartyListAdapter;
import com.example.khataapp.databinding.FragmentHomeBinding;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.User;
import com.example.khataapp.network.ApiClient;
import com.example.khataapp.utils.CONSTANTS;
import com.example.khataapp.utils.DataViewModel;
import com.example.khataapp.utils.SharedPreferenceHelper;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private final int CUSTOMER_FRAGMENT = 1, SUPPLIER_FRAGMENT = 2, ALL_FRAGMENT = 3;
    private FragmentHomeBinding mBinding;
    private NavController navController;
    private String businessName;
    private NavBackStackEntry navBackStackEntry;
    private DataViewModel dataViewModel;
    private LifecycleEventObserver observer;
    private User user = new User();
    private boolean isCustomer = true, isSupplier = false, isAll = false;
    private List<Party> partyList = new ArrayList<>();
    private PartyListAdapter adapter;
    private ProgressDialog progressDialog;
    private boolean isViewReport = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);


        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().show();

        }

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        change_view_by_buttons(CUSTOMER_FRAGMENT);
        MeowBottomNavigation navBar = requireActivity().findViewById(R.id.bottom_view);
        navBar.setVisibility(View.VISIBLE);
        navBar.show(1, true);
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);


        navController = NavHostFragment.findNavController(this);
        navBackStackEntry = navController.getBackStackEntry(R.id.homeFragment);
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);


        getDataFromDialog();
        getUserLiveData();
        setupRecyclerView();

        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().show();

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        btnListener();
        searchViewListener();

        if (isCustomer) {
            getCustomerLiveData();
        } else if (isSupplier) {
            getSupplierLiveData();

        }

    }

    private void searchViewListener() {
        mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
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
                    adapter.setPartyListFull(partyList);
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
                    adapter.setPartyListFull(partyList);
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
                change_view_by_buttons(CUSTOMER_FRAGMENT);
                getCustomerLiveData();
            }
        });
        mBinding.btnSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_view_by_buttons(SUPPLIER_FRAGMENT);
                getSupplierLiveData();
            }
        });
        mBinding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCustomerLiveData();
                change_view_by_buttons(ALL_FRAGMENT);
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

                if (party != null) {
                    HomeFragmentDirections.ActionHomeFragmentToAddPartyFragment action =
                            HomeFragmentDirections.actionHomeFragmentToAddPartyFragment();
                    action.setParty(party);
                    navController.navigate(action);

                }

            }
        });

        mBinding.tvViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showButtonAccordingToViewReports();
            }
        });

    }

    private void showButtonAccordingToViewReports() {

        if (isViewReport) {
            mBinding.cardViewCashRegister.setVisibility(View.VISIBLE);
            mBinding.cardViewCashRegister.setActivated(true);
            mBinding.cardViewBusinessCard.setVisibility(View.VISIBLE);
            mBinding.cardViewBusinessCard.setActivated(true);
            mBinding.cardViewDigiCash.setVisibility(View.VISIBLE);
            mBinding.cardViewDigiCash.setActivated(true);
            if (isAll) {
                mBinding.cardViewMakeInvoice.setVisibility(View.VISIBLE);
                mBinding.cardViewMakeInvoice.setActivated(true);
            }

            isViewReport = false;
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) mBinding.cardViewSearchCustomer.getLayoutParams();
            layoutParams.setMargins(50, 30, 50, 0);
            mBinding.cardViewSearchCustomer.requestLayout();

        } else {
            isViewReport = true;
            mBinding.cardViewCashRegister.setVisibility(View.INVISIBLE);
            mBinding.cardViewCashRegister.setActivated(false);
            mBinding.cardViewBusinessCard.setVisibility(View.INVISIBLE);
            mBinding.cardViewBusinessCard.setActivated(false);
            mBinding.cardViewDigiCash.setVisibility(View.INVISIBLE);
            mBinding.cardViewDigiCash.setActivated(false);


            mBinding.cardViewMakeInvoice.setVisibility(View.GONE);
            mBinding.cardViewMakeInvoice.setActivated(false);
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) mBinding.cardViewSearchCustomer.getLayoutParams();
            layoutParams.setMargins(50, -160, 50, 0);
            mBinding.cardViewSearchCustomer.requestLayout();
        }

    }


    private void change_view_by_buttons(int i) {
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
            isAll = false;

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
            isAll = false;

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
            isAll = false;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.action_refresh).setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_refresh) {
            dataViewModel.deleteParties();
            progressDialog.setMessage("Loading...");
            progressDialog.setTitle("Customer");
            progressDialog.show();
            getParties("c");

        }
        return super.onOptionsItemSelected(item);
    }


    public String getParties(String type) {


        String businessID = SharedPreferenceHelper.getInstance(requireContext()).getBUSINESS_ID();

        Call<GetPartyServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID, type);
        call.enqueue(new Callback<GetPartyServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPartyServerResponse> call, @NonNull Response<GetPartyServerResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        GetPartyServerResponse getPartyServerResponse = response.body();

                        if (getPartyServerResponse.getCode() == 200) {

                            if (getPartyServerResponse.getPartyList() != null && getPartyServerResponse.getPartyList().size() > 0) {

                                dataViewModel.insertParties(getPartyServerResponse.getPartyList());


                            }
                        }

                    }

                } else {
                    if (response.errorBody() != null) {
                        Toast.makeText(requireContext(), "" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressDialog.dismiss();
                if (type.equals("s")) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPartyServerResponse> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if (type.equals("s")) {
                    progressDialog.dismiss();

                }

            }
        });

        if (type.equals("c")) {

            return getParties("s");
        } else {
            return "break";
        }

    }
}