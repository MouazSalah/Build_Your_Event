package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;

public class ForgetPasswordActivity extends AppCompatActivity
{
    @BindView(R.id.forgetpassword_email)
    EditText etEmail;
    UserViewModel viewModel;
    @BindView(R.id.sendcode_progressBar)
    ProgressBar verifyCodeProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPrefMethods prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }


    @OnClick(R.id.forgetpassword_sendcode)
    void sendVerificationCode(View view)
    {
        String email = etEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (etEmail.getText().toString().isEmpty())
        {
            etEmail.setError("enter email");
            return;
        }
        else if (!email.matches(emailPattern))
        {
            etEmail.setError("invalid email");
            return;
        }
        else
        {
            sendCodeTask();
        }
    }

    private void sendCodeTask()
    {
        verifyCodeProgressBar.setVisibility(View.VISIBLE);
        viewModel.sendCode(etEmail.getText().toString()).observe(this, new Observer<SendCodeResponse>()
        {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse)
            {
                if (sendCodeResponse.getStatus() == 200)
                {
                    Intent intent = new Intent(getApplicationContext(), PasswordVerifyCodeActivity.class);
                    intent.putExtra(Codes.RECOVERY_EMAIL, etEmail.getText().toString());
                    startActivity(intent);
                   // customType(ForgetPasswordActivity.this,"left-to-right");
                    verifyCodeProgressBar.setVisibility(View.GONE);
                }
                else
                {
                    verifyCodeProgressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgetPasswordActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
