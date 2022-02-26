package com.example.khataapp.purchase;

import static com.example.khataapp.utils.CONSTANTS.ITEM;
import static com.example.khataapp.utils.CONSTANTS.SELECT_ITEMS_BTN;
import static com.example.khataapp.utils.CONSTANTS.SELECT_SUPPLIER_BTN;
import static com.example.khataapp.utils.CONSTANTS.SUPPLIER;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khataapp.R;
import com.example.khataapp.databinding.FragmentPurchaseBinding;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.Party;
import com.example.khataapp.purchase.viewmodel.PurchaseViewModel;
import com.example.khataapp.utils.CONSTANTS;

import java.util.List;
import java.util.Objects;


public class PurchaseFragment extends Fragment {

    private FragmentPurchaseBinding mBinding;
    private NavController navController;
    private PurchaseViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentPurchaseBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel= new ViewModelProvider(this).get(PurchaseViewModel.class);
        navController = NavHostFragment.findNavController(this);

        mBinding.setViewModel(viewModel);

        getLiveData();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.getBtnAction().setValue(0);
    }

    private void getLiveData() {

        viewModel.getBtnAction().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer action) {

                if (action==2)
                {
                    navController.navigate(R.id.action_purchaseFragment_to_purchaseListFragment);
                }
                else if (action==SELECT_SUPPLIER_BTN)
                {
                    navController.navigate(R.id.action_purchaseFragment_to_supplierListFragment);
                }  else if (action==SELECT_ITEMS_BTN)
                {
                    PurchaseFragmentDirections.ActionPurchaseFragmentToItemListFragment navAction=
                            PurchaseFragmentDirections.actionPurchaseFragmentToItemListFragment();
                    navAction.setKey(1);
                    navController.navigate(navAction);
                }
            }
        });

        MutableLiveData<Party> partyLiveData = Objects.requireNonNull(navController.getCurrentBackStackEntry())
                .getSavedStateHandle()
                .getLiveData(SUPPLIER);
        partyLiveData.observe(getViewLifecycleOwner(), new Observer<Party>() {
            @Override
            public void onChanged(Party model) {
                if (model!=null)
                {
                    viewModel.setParty(model);
                }


            }
        });
        MutableLiveData<List<Item>> ItemsLiveData = Objects.requireNonNull(navController.getCurrentBackStackEntry())
                .getSavedStateHandle()
                .getLiveData(ITEM);
        ItemsLiveData.observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> list) {
                if (list!=null)
                {
                    viewModel.setAdapterList(list);
                }


            }
        });
    }
}