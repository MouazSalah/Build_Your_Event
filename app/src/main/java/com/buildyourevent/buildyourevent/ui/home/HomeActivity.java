package com.buildyourevent.buildyourevent.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.ui.auth.LoginActivity;
import com.buildyourevent.buildyourevent.ui.cardactivity.CartsActivity;
import com.buildyourevent.buildyourevent.ui.CategoriesFragment.HomeFragment;
import com.buildyourevent.buildyourevent.ui.notification.NotificationFragment;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static maes.tech.intentanim.CustomIntent.customType;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.cartscount_textview) TextView cartsCounTextView;
    @BindView(R.id.side_nav_view)
    NavigationView mNavigationView;
   /* @BindView(R.id.nav_userimage) ImageView imageView;
    @BindView(R.id.nav_username) TextView nameText;;
    @BindView(R.id.nav_useremail) TextView emailText;
*/
    SharedPrefMethods prefMethods;
    UserViewModel userViewModel;

    Fragment currentFragment = null;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        prefMethods = new SharedPrefMethods(this);
        UserData userData = prefMethods.getUserData();
        if (userData != null)
        {
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
        }
        else
        {
            cartsCounTextView.setText("0");
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // show navigation drawer on start activity
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(menuItem ->
        {
            switch (menuItem.getItemId())
            {
                case R.id.navigation_side_menu:
                    if (drawer.isDrawerOpen(GravityCompat.START))
                    {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    else
                    {
                        drawer.openDrawer(GravityCompat.START);
                    }
                    break;

                case R.id.navigation_profile:
                    currentFragment = new ProfileFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, currentFragment);
                    ft.commit();
                    return true;

                case R.id.navigation_home:
                    currentFragment = new HomeFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, currentFragment);
                    ft.commit();
                    return true;

                case R.id.navigation_Setting:
                    currentFragment = new SettingFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, currentFragment);
                    ft.commit();
                    return true;

                case R.id.navigation_messages:
                    currentFragment = new NotificationFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, currentFragment);
                    ft.commit();
                    return true;
            }
            return false;
        });
        mNavigationView.setNavigationItemSelectedListener(this);
        View header = mNavigationView.getHeaderView(0);
        TextView mEmailTextView = (TextView) header.findViewById(R.id.nav_useremail);
        TextView mNameTextView = (TextView) header.findViewById(R.id.nav_username);
        ImageView mUserImageView = (ImageView) header.findViewById(R.id.nav_userimage);
        mNameTextView.setText(userData.getName());
        mEmailTextView.setText(userData.getEmail());
        mUserImageView.setImageResource(R.drawable.user_icon);
    }


    @OnClick(R.id.toolbar_menu_icon)
    void openCardItems(View v)
    {
        Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
        startActivity(intent);
        customType(getApplicationContext(),"top-to-bottom");
        finish();
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        int id = menuItem.getItemId();
        if (id == R.id.nav_profileoption)
        {
            currentFragment = new ProfileFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, currentFragment);
            ft.commit();
        }
        if (id == R.id.nav_settinoption)
        {
            currentFragment = new SettingFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, currentFragment);
            ft.commit();
        }
        if (id == R.id.nav_cartsoption)
        {
            Intent intent = new Intent(getApplicationContext(),CartsActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.nav_logoutoption)
        {
            prefMethods.deleteUserData();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}