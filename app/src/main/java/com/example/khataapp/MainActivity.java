package com.example.khataapp;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.khataapp.databinding.ActivityMainBinding;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController= navHostFragment.getNavController();
        }


        setUpBottomNavigation();
        bottomNavigationListener();


    }



    private void bottomNavigationListener() {


        mainBinding.bottomView.setOnClickMenuListener(model -> {

            switch (model.getId())
            {
                case 1:
                {
                    navController.navigate(R.id.homeFragment);
                    break;
                }
                case 2:
                {
                    navController.navigate(R.id.moneyFragment);
                    break;
                }
                case 3:
                {
                    navController.navigate(R.id.moreFragment);
                    break;
                }
            }
            return null;
        });

    }

    private void setUpBottomNavigation() {


        mainBinding.bottomView.add(new MeowBottomNavigation.Model(1,R.drawable.ic_homepage));
        mainBinding.bottomView.add(new MeowBottomNavigation.Model(2,R.drawable.ic_money_bag));
        mainBinding.bottomView.add(new MeowBottomNavigation.Model(3,R.drawable.ic_more));
    }
}