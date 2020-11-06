package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.resetpassword.ResetPasswordResponse;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.facebook.login.Login;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;

public class NewPasswordActivity extends AppCompatActivity
{
    UserViewModel viewModel;
    @BindView(R.id.newpassword_edittext) EditText newPassword;
    @BindView(R.id.confirmnewpassword_edtittext) EditText confirmPasswordEditText;

    String recoveryEmail;

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
        setContentView(R.layout.activity_new_password);
        ButterKnife.bind(this);

        recoveryEmail = getIntent().getStringExtra("recovery_email");

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

    }

    @OnClick(R.id.submitnewpassword_button)
    void submitNewPassowrd(View v)
    {
        if (newPassword.getText().toString().isEmpty())
        {
            newPassword.setError("enter password");
        }
        else if (confirmPasswordEditText.getText().toString().isEmpty())
        {
            newPassword.setError("enter password again");
        }
        else if (newPassword.getText().toString().isEmpty())
        {
            newPassword.setError("enter password");
        }
        else if (newPassword.getText().toString().isEmpty())
        {
            newPassword.setError("enter password");
        }
        else
        {
            resetPasswordTask();
        }
    }

    private void resetPasswordTask()
    {
        viewModel.resetPassword(recoveryEmail, confirmPasswordEditText.getText().toString()).observe(this, new Observer<ResetPasswordResponse>() {
            @Override
            public void onChanged(ResetPasswordResponse resetPasswordResponse) {
                if (resetPasswordResponse.getStatus() == 200)
                {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    customType(NewPasswordActivity.this,"left-to-right");
                }
                else
                {
                    Toast.makeText(NewPasswordActivity.this, "" + resetPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
