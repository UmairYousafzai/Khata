
package com.example.khataapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.khataapp.databinding.FragmentPartyFullInfoBinding;
import com.example.khataapp.models.Party;

import java.util.Objects;

public class PartyFullInfoFragment extends Fragment {

    private FragmentPartyFullInfoBinding mBinding;
    private Party party;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mBinding = FragmentPartyFullInfoBinding.inflate(inflater,container,false);
       return  mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        if (getArguments()!=null)
        {
            party= PartyFullInfoFragmentArgs.fromBundle(getArguments()).getParty();
        }

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(party.getPartyName());
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.action_edit).setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.action_edit)
        {
            PartyFullInfoFragmentDirections.ActionPartyFullInfoToAddPartyFragment action = PartyFullInfoFragmentDirections.actionPartyFullInfoToAddPartyFragment();
            action.setParty(party);

            navController.navigate(action);
        }

        return super.onOptionsItemSelected(item);
    }
}