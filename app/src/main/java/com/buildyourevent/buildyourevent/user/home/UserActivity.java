package com.buildyourevent.buildyourevent.user.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.ActivityUserBinding;
import com.buildyourevent.buildyourevent.databinding.CartIconLayoutBinding;
import com.buildyourevent.buildyourevent.databinding.NavHeaderBinding;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.ui.CategoriesFragment.HomeFragment;
import com.buildyourevent.buildyourevent.ui.auth.LoginActivity;
import com.buildyourevent.buildyourevent.ui.cardactivity.CartsActivity;
import com.buildyourevent.buildyourevent.ui.home.AboutAppActivity;
import com.buildyourevent.buildyourevent.ui.home.ProfileFragment;
import com.buildyourevent.buildyourevent.ui.home.SettingFragment;
import com.buildyourevent.buildyourevent.ui.notification.NotificationFragment;
import com.buildyourevent.buildyourevent.ui.userproducts.MyProductsActivity;
import com.buildyourevent.buildyourevent.utils.MovementManager;
import com.buildyourevent.buildyourevent.utils.PrefMethods;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.utils.UserInfo;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    ActivityUserBinding binding;
    NavHeaderBinding navHeaderBinding;
    CartIconLayoutBinding cartBinding;
    NavigationView mNavigationView;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale(PrefMethods.getInstance().getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        binding = DataBindingUtil.setContentView(this,R.layout.activity_user);
        navHeaderBinding = DataBindingUtil.setContentView(this, R.layout.nav_header);
        cartBinding = DataBindingUtil.setContentView(this, R.layout.cart_icon_layout);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        mNavigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navView = binding.navView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_side_menu:
                    if (binding.userDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.userDrawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        binding.userDrawerLayout.openDrawer(GravityCompat.START);
                    }
                    break;

                case R.id.navigation_profile:
                    MovementManager.replaceFragment(this, new ProfileFragment(), R.id.nav_host_fragment,"ProfileFragment");
                    return true;

                case R.id.navigation_home:
                    MovementManager.replaceFragment(this, new HomeFragment(), R.id.nav_host_fragment,"HomeFragment");

                    return true;

                case R.id.navigation_Setting:
                    MovementManager.replaceFragment(this, new SettingFragment(), R.id.nav_host_fragment,"SettingFragment");
                    return true;

                case R.id.navigation_messages:
                    MovementManager.replaceFragment(this, new NotificationFragment(), R.id.nav_host_fragment,"NotificationFragment");

                    return true;
            }
            return false;
        });

        UserData userData = PrefMethods.getInstance().getUserData();
        if (userData != null)
        {
            navHeaderBinding.navUsername.setText(userData.getName());
            navHeaderBinding.navUseremail.setText(userData.getEmail());
            navHeaderBinding.navUserimage.setImageResource(R.drawable.white);

            userViewModel.getAllCarts(userData.getId(), userData.getToken()).observe(this, new Observer<CartResponse>() {
                @Override
                public void onChanged(CartResponse cartResponse)
                {
                    if (cartResponse.getStatus() == 200)
                    {
                        int size = cartResponse.getData().size();
                        PrefMethods.getInstance().saveCartsCount(size);
                         cartBinding.cartscountTextview.setText("" + size);
                    } else {
                        cartBinding.cartscountTextview.setText("0");
                    }
                }
            });
        }
        else
        {
            cartBinding.cartscountTextview.setText("0");
        }
    }


    @Override
    public void onBackPressed() {
        if (binding.userDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.userDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        int id = menuItem.getItemId();
        if (id == R.id.nav_profileoption)
        {
            MovementManager.replaceFragment(this, new ProfileFragment(), R.id.nav_host_fragment,"ProfileFragment");
        }
        if (id == R.id.nav_productsoption)
        {
            Intent intent = new Intent(getApplicationContext(), MyProductsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_settinoption)
        {
            MovementManager.replaceFragment(this, new SettingFragment(), R.id.nav_host_fragment,"SettingFragment");
        }
        if (id == R.id.nav_cartsoption)
        {
            Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_logoutoption)
        {
            PrefMethods.getInstance().deleteUserData();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_aboutusoption)
        {
            Intent intent = new Intent(getApplicationContext(), AboutAppActivity.class);
            startActivity(intent);
        }
        binding.userDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
