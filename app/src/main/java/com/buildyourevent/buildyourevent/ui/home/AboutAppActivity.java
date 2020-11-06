package com.buildyourevent.buildyourevent.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.data.aboutus.AboutUsResponse;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;

import butterknife.BindView;

public class AboutAppActivity extends AppCompatActivity
{
    UserViewModel viewModel;
    @BindView(R.id.aboutus_textview)
    TextView aboutUsText;

    @BindView(R.id.aboutus_title)
    TextView aboutUsTitle;
    @BindView(R.id.about_image)
    ImageView aboutImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);


        viewModel.getAboutUs().observe(this, new Observer<AboutUsResponse>() {
            @Override
            public void onChanged(AboutUsResponse response )
            {
                Glide.with(getApplicationContext()).load(response.getAboutData().getImage()).into(aboutImage);
                aboutUsText.setText("" + response.getAboutData().getText());
                aboutUsTitle.setText("" + response.getAboutData().getName());
            }
        });
    }
}
