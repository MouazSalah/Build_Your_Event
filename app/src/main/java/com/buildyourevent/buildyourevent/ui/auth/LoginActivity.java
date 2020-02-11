package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.model.auth.login.LoginRequest;
import com.buildyourevent.buildyourevent.model.auth.login.LoginResponse;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;

public class LoginActivity extends AppCompatActivity
{
    @BindView(R.id.loginemail_edittext) EditText emailEditText;
    @BindView(R.id.loginpassword_edittext) EditText passwordEditText;
    @BindView(R.id.login_remember_me) CheckBox rememberMeCheckBox;

    UserViewModel viewModel;
    String userEMail, userPassword;
    SharedPrefMethods prefMethods;
    UserData userData = new UserData();
    @BindView(R.id.login_progressbar)
    ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userData = prefMethods.getUserData();
        if (userData != null)
        {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            return;
        }

        putDataToFields();

    }

    @OnClick(R.id.login_continue_button)
    void onContinueButtonClicked(View v)
    {
        loginProgressBar.setVisibility(View.VISIBLE);
        if (checkMandatoryFields())
        {
            LoginRequest loginRequest = new LoginRequest(emailEditText.getText().toString(), passwordEditText.getText().toString());
            viewModel.loginCurrentUser(loginRequest).observe(this, new Observer<LoginResponse>() {
                @Override
                public void onChanged(LoginResponse loginResponse)
                {
                    Log.d(Codes.APP_TAGS,  "onlogin" + loginResponse.getStatus());
                    if (loginResponse.getStatus() == 200)
                    {
                        Log.d(Codes.APP_TAGS,  "onlogin" + loginResponse.getUserData().toString());
                        prefMethods.SaveUserData(loginResponse.getUserData());
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        customType(LoginActivity.this,"bottom-to-up");
                        loginProgressBar.setVisibility(View.GONE);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        loginProgressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }


    public boolean checkMandatoryFields()
    {
        String email = emailEditText.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailEditText.getText().toString().isEmpty())
        {
            emailEditText.setError("enter email");
            return false;
        }
        else if (passwordEditText.getText().toString().isEmpty())
        {
            passwordEditText.setError("enter password");
            return false;
        }
        else if (!email.matches(emailPattern))
        {
            emailEditText.setError("invalid email");
            return false;
        }
        else if (passwordEditText.getText().toString().trim().length() < 6)
        {
            passwordEditText.setError("short password");
            return false;
        }
        else
        {
            return true;
        }
    }


    private void putDataToFields()
    {
       emailEditText.setText(userEMail);
       passwordEditText.setText(userPassword);

    }


    @OnClick(R.id.login_create_account_textview)
    void startRegisterIntent()
    {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        customType(LoginActivity.this,"bottom-to-up");
    }

    @OnClick(R.id.login_skip_login)
    void skipLoginTask(View v)
    {
        Intent intent = new Intent(getApplicationContext(), ChooseCountryActivity.class);
        startActivity(intent);
        customType(LoginActivity.this,"bottom-to-up");
    }


    @OnClick(R.id.login_forget_password)
    void forgetPasswordTask()
    {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
        customType(LoginActivity.this,"bottom-to-up");
    }
}
