package com.example.khataapp.views.sale;

import static com.example.khataapp.utils.CONSTANTS.DOCUMENT;

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

import com.example.khataapp.databinding.FragmentSaleDocListBinding;
import com.example.khataapp.models.Document;
import com.example.khataapp.views.sale.viewModel.SaleDocListViewModel;


public class SaleDocListFragment extends Fragment {

    private FragmentSaleDocListBinding mBinding;
    private SaleDocListViewModel viewModel;
    private NavController navController ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentSaleDocListBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel= new ViewModelProvider(this).get(SaleDocListViewModel.class);
        navController= NavHostFragment.findNavController(this);

        mBinding.setSaleViewModel(viewModel);



        getLiveData();
    }

    private void getLiveData() {
        if (getArguments()!=null)
        {
            viewModel.getDocument(SaleDocListFragmentArgs.fromBundle(getArguments()).getDocType());
        }

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {

                if (message!=null)
                {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getDocumentMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Document>() {
            @Override
            public void onChanged(Document document) {
                if (document !=null)
                {
                    if (navController.getPreviousBackStackEntry()!=null)
                    {
                        navController.getPreviousBackStackEntry().getSavedStateHandle().set(DOCUMENT, document);
                        navController.popBackStack();

                    }
                }
            }
        });
    }
}