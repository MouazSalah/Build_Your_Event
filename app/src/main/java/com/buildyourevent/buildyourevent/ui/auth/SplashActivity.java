package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;

public class SplashActivity extends AppCompatActivity
{
    @BindView(R.id.splash_layout) LinearLayout splashLayout;

    SharedPrefMethods sharedPrefMethods;
    String preferedLang ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_splash);
        ButterKnife.bind(this);

        sharedPrefMethods = new SharedPrefMethods(this);
        sharedPrefMethods.deleteUserLanguage();
        preferedLang = sharedPrefMethods.getUserLanguage();
        /*if (!preferedLang.equals(null))
        {
            Log.d(Codes.APP_TAGS, "language: " + preferedLang);
            changeLanguage(preferedLang);
        }*/
    }

    @OnClick(R.id.arabic_language_button)
    public void onArabicBtnClicked(View view)
    {
        changeLanguage("ar");
    }

    @OnClick(R.id.english_language_button)
    public void onEnglishBtnClicked(View view)
    {
        changeLanguage("en");
    }

    public void changeLanguage(String lang)
    {
        sharedPrefMethods.saveUserLanguage(lang);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        if (lang.equals("ar"))
        {
            customType(SplashActivity.this,"left-to-right");
        }
        else
        {
            customType(SplashActivity.this,"right-to-left");
        }

        finish();
    }


}


