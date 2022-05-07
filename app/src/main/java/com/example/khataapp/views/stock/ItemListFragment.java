package com.example.khataapp.views.stock;

import static com.example.khataapp.utils.CONSTANTS.ITEM;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.khataapp.R;
import com.example.khataapp.databinding.FragmentItemListBinding;
import com.example.khataapp.models.Item;
import com.example.khataapp.views.stock.viewmodel.StockViewModel;

import java.util.List;
import java.util.Objects;

public class ItemListFragment extends Fragment {

    private FragmentItemListBinding mBinding;
    private StockViewModel viewModel;
    private NavController navController;
    private int key=0;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentItemListBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController= NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this).get(StockViewModel.class);

        mBinding.setViewModel(viewModel);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Items");
        progressDialog.setMessage("Loading");


        viewModel.getItemsList();

        if (getArguments()!=null)
        {
            key=ItemListFragmentArgs.fromBundle(getArguments()).getKey();
            if (key==1)
            {
                mBinding.btnAdd.setVisibility(View.GONE);
                viewModel.setAdapterKey(key);
                mBinding.btnSelect.setVisibility(View.VISIBLE);
            }
        }

        getLiveData();

        btnListener();
        closeKeyBoard();
    }

    private void btnListener() {

        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_itemListFragment_to_addItemFragment);
            }
        });
    }

    private void getLiveData() {

        viewModel.getServerErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s!=null)
                {
                    Toast.makeText(requireContext(), ""+s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getItemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(Item item) {

                if (item!=null)
                {
                    if (item.getBtn_action()==2){
                        ItemListFragmentDirections.ActionItemListFragmentToAddItemFragment action=
                                ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(item.getBarcode());

                        viewModel.getItemMutableLiveData().setValue(null);
                        navController.navigate(action);
                    }


                }
            }
        });

        viewModel.getItemListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {

                if (items!=null&& items.size()>0)
                {

                        Objects.requireNonNull(navController.getPreviousBackStackEntry()).getSavedStateHandle().set(ITEM,items);
                        navController.popBackStack();
                        closeKeyBoard();
                }
            }
        });

        viewModel.getSelectedBtnText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s!=null)
                {
                    mBinding.btnSelect.setText(s);
                }
            }
        });

        viewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {

                if (show)
                {
                    progressDialog.show();
                }
                else
                {
                    progressDialog.dismiss();
                }
            }
        });

    }

    public void closeKeyBoard() {
        View view = requireActivity().getCurrentFocus();

        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }
}