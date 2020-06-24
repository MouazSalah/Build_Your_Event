package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.ActivitySplashBinding;
import com.buildyourevent.buildyourevent.user.auth.activity.AuthenticateActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;

public class SplashActivity extends AppCompatActivity
{
    SharedPrefMethods prefMethods;
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        prefMethods = new SharedPrefMethods(this);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        a.reset();
        a.setDuration(2000);

        binding.ivSplash.clearAnimation();
        binding.ivSplash.startAnimation(a);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(getApplicationContext(), AuthenticateActivity.class));
/*

                if (prefMethods.getUserLanguage() == null)
                {
                    startActivity(new Intent(getApplicationContext(), AuthenticateActivity.class));
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));
                }
*/
            }
        }, 3000);
    }
}


