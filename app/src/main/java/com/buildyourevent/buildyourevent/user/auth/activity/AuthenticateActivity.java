package com.buildyourevent.buildyourevent.user.auth.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.ActivityAuthenticateBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class AuthenticateActivity extends AppCompatActivity
{
    ActivityAuthenticateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_authenticate);

       // NavController navController = Navigation.findNavController(this, R.id.auth_host_fragment);

    }
}