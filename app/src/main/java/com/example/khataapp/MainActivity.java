package com.example.khataapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.khataapp.databinding.ActivityMainBinding;
import com.example.khataapp.utils.DataViewModel;
import com.example.khataapp.utils.SharedPreferenceHelper;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private NavController navController;
    private DataViewModel dataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);


        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }


        mainBinding.bottomView.setVisibility(View.GONE);
        setUpBottomNavigation();
        bottomNavigationListener();

        setupToolbar();
        fragmentListener();

    }

    private void fragmentListener() {

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {

                if (destination.getId() == R.id.homeFragment) {
                    mainBinding.bottomView.setVisibility(View.VISIBLE);
                    mainBinding.bottomView.show(1, true);
                } else if (destination.getId() == R.id.moneyFragment) {
                    mainBinding.bottomView.setVisibility(View.VISIBLE);
                    mainBinding.bottomView.show(2, true);


                } else if (destination.getId() == R.id.moreFragment) {
                    mainBinding.bottomView.setVisibility(View.VISIBLE);
                    mainBinding.bottomView.show(3, true);

                } else {
                    mainBinding.bottomView.setVisibility(View.GONE);
                }

                if (destination.getId() == R.id.splashScreenFragment) {
                    mainBinding.toolbar.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.loginFragment) {
                    mainBinding.toolbar.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.signUpFragment) {
                    mainBinding.toolbar.setVisibility(View.GONE);
                } else {
                    mainBinding.toolbar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setupToolbar() {
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.homeFragment, R.id.moneyFragment, R.id.moreFragment).build();
        Toolbar toolbar = mainBinding.toolbar;

        setSupportActionBar(toolbar);


        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
    }


    private void bottomNavigationListener() {


        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();


        mainBinding.bottomView.setOnClickMenuListener((MeowBottomNavigation.Model model) -> {

            switch (model.getId()) {
                case 1: {
                    navController.navigate(R.id.homeFragment, null, navOptions);
                    break;
                }
                case 2: {
                    navController.navigate(R.id.moneyFragment, null, navOptions);
                    break;
                }
                case 3: {
                    navController.navigate(R.id.moreFragment, null, navOptions);
                    break;
                }
            }
            return null;
        });

    }

    private void setUpBottomNavigation() {

        mainBinding.bottomView.add(new MeowBottomNavigation.Model(1, R.drawable.ic_homepage));
        mainBinding.bottomView.add(new MeowBottomNavigation.Model(2, R.drawable.ic_money_bag));
        mainBinding.bottomView.add(new MeowBottomNavigation.Model(3, R.drawable.ic_more));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_refresh).setVisible(false);
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_call).setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_signout) {
            signOut();
        } else if (item.getItemId() == R.id.action_department) {
            navController.navigate(R.id.addDepartmentFragment);
        } else if (item.getItemId() == R.id.action_purchase) {
            navController.navigate(R.id.purchaseFragment);
        } else if (item.getItemId() == R.id.action_sale) {
            navController.navigate(R.id.saleDocFragment);
        } else if (item.getItemId() == R.id.action_sale_return) {
            navController.navigate(R.id.saleReturnDocFragment);
        } else if (item.getItemId() == R.id.action_purchase_return) {
            navController.navigate(R.id.purchaseReturnFragment);
        }else if (item.getItemId() == R.id.action_item) {
            navController.navigate(R.id.itemListFragment);
        }

        return super.onOptionsItemSelected(item);

    }

    public void signOut() {
        dataViewModel.deleteParties();
        dataViewModel.deleteUser();
        SharedPreferenceHelper.getInstance(this).setIsLogin(false);
        SharedPreferenceHelper.getInstance(this).setBUSINESS_ID("");
        SharedPreferenceHelper.getInstance(this).setUserID("");
        navController.navigate(R.id.splashScreenFragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        int currentFragmentID= navController.getCurrentDestination().getId();

        if (currentFragmentID==R.id.splashScreenFragment)
        {
            finish();
        }


    }
}