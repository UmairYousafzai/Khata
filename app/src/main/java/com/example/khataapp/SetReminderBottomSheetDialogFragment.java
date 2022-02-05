package com.example.khataapp;

import static com.example.khataapp.utils.CONSTANTS.DATE_KEY;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.khataapp.databinding.FragmentSetReminderBottomSheetDialogBinding;
import com.example.khataapp.utils.CONSTANTS;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SetReminderBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private FragmentSetReminderBottomSheetDialogBinding mBinding;
    private NavController navController;
    private String date;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentSetReminderBottomSheetDialogBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        initViews();
        btnListener();
    }

    private void btnListener() {
        mBinding.radioBtnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.radioBtnCalender.isChecked())
                {

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                            SimpleDateFormat format = new SimpleDateFormat("dd MMM ");

                            calendar.set(year, month, dayOfMonth);


                             date = format.format(calendar.getTime());

                            if (navController.getPreviousBackStackEntry()!=null)
                            {
                                navController.getPreviousBackStackEntry().getSavedStateHandle().set(DATE_KEY,date);
                                navController.popBackStack();
                            }


                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });
        mBinding.radioBtnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.radioBtnNextMonth.isChecked())
                {
                    if (mBinding.tvNextMonthDate.getText()!=null && mBinding.tvNextMonthDate.getText().toString().length()>0)
                    {
                        date= mBinding.tvNextMonthDate.getText().toString();
                        if (navController.getPreviousBackStackEntry()!=null)
                        {
                            navController.getPreviousBackStackEntry().getSavedStateHandle().set(DATE_KEY,date);
                            navController.popBackStack();
                        }
                    }
                }
            }
        });

        mBinding.radioBtnNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.radioBtnNextWeek.isChecked())
                {
                    if (mBinding.tvNextWeekDate.getText()!=null && mBinding.tvNextWeekDate.getText().toString().length()>0)
                    {
                        date= mBinding.tvNextWeekDate.getText().toString();
                        if (navController.getPreviousBackStackEntry()!=null)
                        {
                            navController.getPreviousBackStackEntry().getSavedStateHandle().set(DATE_KEY,date);
                            navController.popBackStack();
                        }
                    }
                }
            }
        });

        mBinding.radioBtnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();

                int Day= calendar.get(Calendar.DAY_OF_MONTH);
                int Month=calendar.get(Calendar.MONTH);
                int Year= calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                        calendar.set(year,month,dayOfMonth);


                            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");



                            String date = format.format(calendar.getTime());

                        if (navController.getPreviousBackStackEntry()!=null)
                        {
                            navController.getPreviousBackStackEntry().getSavedStateHandle().set(DATE_KEY,date);
                            navController.popBackStack();
                        }

                        }



                }, Year, Month, Day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    private void initViews() {

        Calendar calendar= Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+7);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        String date = dateFormat.format(calendar.getTime());

        mBinding.tvNextWeekDate.setText(date);

        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-7);
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+1);

        date = dateFormat.format(calendar.getTime());

        mBinding.tvNextMonthDate.setText(date);

    }
}