package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.model.auth.login.LoginRequest;
import com.buildyourevent.buildyourevent.model.auth.login.LoginResponse;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.AuthViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;

public class LoginActivity extends AppCompatActivity
{
    @BindView(R.id.loginemail_edittext) EditText emailEditText;
    @BindView(R.id.loginpassword_edittext) EditText passwordEditText;
    @BindView(R.id.login_remember_me) CheckBox rememberMeCheckBox;

    AuthViewModel viewModel;

    String userEMail, userPassword;

    SharedPrefMethods prefMethods;
    UserData userData = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        prefMethods = new SharedPrefMethods(this);
        userData = prefMethods.getUserData();
        if (userData != null)
        {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        putDataToFields();

        /*viewModel.getViewModelLiveData().observe(this, new Observer<Object>()
        {
            @Override
            public void onChanged(Object o)
            {
                if (o instanceof String)
                {
                    String result = (String) o;
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                }
                else if (o instanceof Integer)
                {
                    int result = (Integer) o;
                    if (result == Codes.RESPONSE_FAILED)
                    {
                        Toast.makeText(LoginActivity.this, "Try later...", Toast.LENGTH_SHORT).show();
                    }
                    else if (result == Codes.RESPONSE_SUCCESS)
                    {
                        if (rememberMeCheckBox.isChecked())
                        {
                           // prefMethods.SaveUserData();
                        }
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });*/
    }

    @OnClick(R.id.login_continue_button)
    void onContinueButtonClicked(View v)
    {
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
                        finish();
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
        finish();
    }

    @OnClick(R.id.login_skip_login)
    void skipLoginTask(View v)
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        customType(LoginActivity.this,"bottom-to-up");
        finish();
    }


    @OnClick(R.id.login_forget_password)
    void forgetPasswordTask()
    {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
      //  customType(getApplicationContext(),"bottom-to-up");
        finish();
    }
}
