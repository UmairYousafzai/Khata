
package com.example.khataapp.views.party;

import static com.example.khataapp.utils.CONSTANTS.DATE_KEY;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.khataapp.R;
import com.example.khataapp.databinding.FragmentPartyFullInfoBinding;
import com.example.khataapp.models.Party;
import com.example.khataapp.utils.MessageUtil;
import com.example.khataapp.viewModel.PartyViewModel;


import java.util.Objects;

public class PartyFullInfoFragment extends Fragment {

    private FragmentPartyFullInfoBinding mBinding;
    private Party party;
    private NavController navController;
    private NavBackStackEntry navBackStackEntry;
    private LifecycleEventObserver observer;
    private String date;
    private PartyViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mBinding = FragmentPartyFullInfoBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        navBackStackEntry = navController.getBackStackEntry(R.id.partyFullInfoFragment);
        viewModel = new ViewModelProvider(this).get(PartyViewModel.class);
        mBinding.setViewModel(viewModel);


        if (getArguments() != null) {
            party = PartyFullInfoFragmentArgs.fromBundle(getArguments()).getParty();
            if (party != null) {
                viewModel.voucherDetail(party.getPartyCode());
                setUi(party);
            }
        }

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(party.getPartyName());

        getDataFromDialog();
        btnClickListener();
        liveDataObservers();
    }

    private void setUi(Party party) {

        if (party.getPartyType().equals("s")) {
            mBinding.btnReceipt.setVisibility(View.GONE);
        } else {
            mBinding.btnPayment.setVisibility(View.GONE);
        }

    }

    private void liveDataObservers() {

        viewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), flag -> {

        });


    }

    private void btnClickListener() {

        mBinding.tvDialogOpener.setOnClickListener(v -> navController.navigate(R.id.action_partyFullInfoFragment_to_setReminderBottomSheetDialogFragment));

        mBinding.tvDialogOpener1.setOnClickListener(v -> navController.navigate(R.id.action_partyFullInfoFragment_to_setReminderBottomSheetDialogFragment));

        mBinding.btnPayment.setOnClickListener(view -> {

            PartyFullInfoFragmentDirections.ActionPartyFullInfoFragmentToAddAmountFragment action =
                    PartyFullInfoFragmentDirections.actionPartyFullInfoFragmentToAddAmountFragment(party);

            navController.navigate(action);
        });
        mBinding.btnReceipt.setOnClickListener(view -> {

            PartyFullInfoFragmentDirections.ActionPartyFullInfoFragmentToAddAmountFragment action =
                    PartyFullInfoFragmentDirections.actionPartyFullInfoFragmentToAddAmountFragment(party);

            navController.navigate(action);
        });

        mBinding.btnWhatsappReminder.setOnClickListener(view -> {
            MessageUtil.getInstance().sendwts(requireContext(),party.getMobile());
        });
    }

    private void getDataFromDialog() {


        observer = (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_RESUME) && navBackStackEntry.getSavedStateHandle().contains(DATE_KEY)) {

                date = "Collection Date: " + navBackStackEntry.getSavedStateHandle().get(DATE_KEY);

                mBinding.tvDialogOpener.setText(date);
                mBinding.tvDialogOpener1.setText("");
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow_forward_blue, null);
                mBinding.tvDialogOpener1.setCompoundDrawables(null, null, drawable, null);

            }
        };
        navBackStackEntry.getLifecycle().addObserver(observer);

        // As addObserver() does not automatically remove the observer, we
        // call removeObserver() manually when the view lifecycle is destroyed
        getViewLifecycleOwner().getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                navBackStackEntry.getLifecycle().removeObserver(observer);

            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.action_edit).setVisible(true);
        menu.findItem(R.id.action_call).setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {
            PartyFullInfoFragmentDirections.ActionPartyFullInfoToAddPartyFragment action = PartyFullInfoFragmentDirections.actionPartyFullInfoToAddPartyFragment();
            action.setParty(party);

            navController.navigate(action);
        }

        return super.onOptionsItemSelected(item);
    }
}