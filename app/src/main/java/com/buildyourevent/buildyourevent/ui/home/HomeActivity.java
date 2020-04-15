package com.buildyourevent.buildyourevent.ui.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.ui.auth.LoginActivity;
import com.buildyourevent.buildyourevent.ui.cardactivity.CartsActivity;
import com.buildyourevent.buildyourevent.ui.CategoriesFragment.HomeFragment;
import com.buildyourevent.buildyourevent.ui.notification.NotificationFragment;
import com.buildyourevent.buildyourevent.ui.userproducts.MyProductsActivity;
import com.buildyourevent.buildyourevent.utils.MovementManager;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.cartscount_textview)
    TextView cartsCounTextView;
    @BindView(R.id.side_nav_view)
    NavigationView mNavigationView;
    /* @BindView(R.id.nav_userimage) ImageView imageView;
     @BindView(R.id.nav_username) TextView nameText;;
     @BindView(R.id.nav_useremail) TextView emailText;
 */
    SharedPrefMethods prefMethods;
    UserViewModel userViewModel;

//    Fragment currentFragment = null;
//    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        prefMethods = new SharedPrefMethods(this);

        mNavigationView.setNavigationItemSelectedListener(this);
        View header = mNavigationView.getHeaderView(0);
        TextView mEmailTextView = (TextView) header.findViewById(R.id.nav_useremail);
        TextView mNameTextView = (TextView) header.findViewById(R.id.nav_username);
        ImageView mUserImageView = (ImageView) header.findViewById(R.id.nav_userimage);
        ImageView hideNacImageView = (ImageView) header.findViewById(R.id.hide_navbarimage);
        hideNacImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        UserData userData = prefMethods.getUserData();
        if (userData != null) {
            mNameTextView.setText(userData.getName());
            mEmailTextView.setText(userData.getEmail());
            mUserImageView.setImageResource(R.drawable.white);

            userViewModel.getAllCarts(userData.getId(), userData.getToken()).observe(this, new Observer<CartResponse>() {
                @Override
                public void onChanged(CartResponse cartResponse) {
                    if (cartResponse.getStatus() == 200) {
                        int size = cartResponse.getData().size();
                        cartsCounTextView.setText("" + size);
                    } else {
                        cartsCounTextView.setText("0");
                    }
                }
            });

            /*imageView.setImageResource(R.drawable.user_icon);
            emailText.setText(userData.getEmail());
            nameText.setText(userData.getName());*/

            /*userViewModel.getAllCarts(userData.getId(), userData.getToken()).observe(this, new Observer<CartResponse>() {
                @Override
                public void onChanged(CartResponse cartResponse)
                {
                    if (cartResponse.getStatus() == 200)
                    {
                        cartsCounTextView.setText("" + cartResponse.getData().size());
                    }
                }
            });*/
        } else {
            cartsCounTextView.setText("0");
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // show navigation drawer on start activity
        NavigationUI.setupWithNavController(navView, navController);

//        ft = getSupportFragmentManager().beginTransaction();

        navView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_side_menu:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        drawer.openDrawer(GravityCompat.START);
                    }
                    break;

                case R.id.navigation_profile:  //this is wrong   why profile is in side menu and bottom bar !!
                    //make this to just one of them not both
//                    currentFragment = new ProfileFragment();
//                    // ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.nav_host_fragment, currentFragment);
//                    ft.commit();
                    MovementManager.replaceFragment(this, new ProfileFragment(), R.id.nav_host_fragment,"ProfileFragment");
                    return true;
                case R.id.navigation_home:
//                    currentFragment = new HomeFragment();
//                    //  ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.nav_host_fragment, currentFragment);
//                    ft.commit();

                    MovementManager.replaceFragment(this, new HomeFragment(), R.id.nav_host_fragment,"");

                    return true;

                case R.id.navigation_Setting:
//                    currentFragment = new SettingFragment();
//                    //  ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.nav_host_fragment, currentFragment);
//                    ft.commit();
                    MovementManager.replaceFragment(this, new SettingFragment(), R.id.nav_host_fragment,"SettingFragment");

                    return true;

                case R.id.navigation_messages:
//                    currentFragment = new NotificationFragment();
//                    ft.replace(R.id.nav_host_fragment, currentFragment);
//                    ft.commit();
                    MovementManager.replaceFragment(this, new NotificationFragment(), R.id.nav_host_fragment,"NotificationFragment");

                    return true;
            }
            return false;
        });

    }


    @OnClick(R.id.toolbar_menu_icon)
    void openCardItems(View v) {
        Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
        startActivity(intent);
        customType(HomeActivity.this, "top-to-bottom");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }


    // hide navigation drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

//        int count = getSupportFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getSupportFragmentManager().popBackStack();
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_profileoption) {
//            currentFragment = new ProfileFragment();
//            ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_host_fragment, currentFragment);
//            ft.commit();
            MovementManager.replaceFragment(this, new ProfileFragment(), R.id.nav_host_fragment,"ProfileFragment");
        }
        if (id == R.id.nav_productsoption)
        {
            Intent intent = new Intent(getApplicationContext(), MyProductsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_settinoption) {
//            currentFragment = new SettingFragment();
//            ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_host_fragment, currentFragment);
//            ft.commit();
            MovementManager.replaceFragment(this, new SettingFragment(), R.id.nav_host_fragment,"SettingFragment");

        }
        if (id == R.id.nav_cartsoption) {
            Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_logoutoption) {
            prefMethods.deleteUserData();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_aboutusoption) {
            Intent intent = new Intent(getApplicationContext(), AboutAppActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}