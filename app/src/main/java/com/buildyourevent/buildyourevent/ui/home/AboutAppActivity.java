package com.buildyourevent.buildyourevent.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.aboutus.AboutUsResponse;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.Locale;

import butterknife.BindView;

public class AboutAppActivity extends AppCompatActivity
{
    UserViewModel viewModel;
    @BindView(R.id.aboutus_textview)
    TextView aboutUsText;

    @BindView(R.id.aboutus_title)
    TextView aboutUsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);


       /* viewModel.getAboutUs().observe(this, new Observer<AboutUsResponse>() {
            @Override
            public void onChanged(AboutUsResponse response )
            {
                    aboutUsText.setText("" + response.getData().get(1).getText());
                    aboutUsTitle.setText("" + response.getData().get(1).getName());
            }
        });*/
    }
}
