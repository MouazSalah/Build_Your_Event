package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.code.VerifyCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.AuthViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;

public class VerifyCodeActivity extends AppCompatActivity
{
    @BindView(R.id.verifycode_code) EditText etCode;
    @BindView(R.id.verifycode_text) TextView tvCodeText;

    String recoveryEmail;
    String verifyCodeIntent;
    AuthViewModel viewModel;
    SharedPrefMethods prefMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        ButterKnife.bind(this);

        prefMethods = new SharedPrefMethods(this);
        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        recoveryEmail = getIntent().getStringExtra(Codes.RECOVERY_EMAIL);
        verifyCodeIntent = getIntent().getStringExtra(Codes.VERIFY_CODE_INTENT);

        tvCodeText.append(recoveryEmail);
    }


    @OnClick(R.id.verifycode_confirm)
    void confirmVerification(View v)
    {
        if (!etCode.getText().toString().isEmpty())
        {
            verifyCode();
        }
    }

    private void verifyCode()
    {
        viewModel.verifyCode(recoveryEmail, Integer.parseInt(etCode.getText().toString())).observe(this, new Observer<VerifyCodeResponse>() {
            @Override
            public void onChanged(VerifyCodeResponse verifyCodeResponse)
            {
                if (verifyCodeResponse.getStatus() == 201)
                {
                    if (verifyCodeIntent.equals("register"))
                    {
                        //UserData userData =  verifyCodeResponse.getData();
                        //prefMethods.SaveUserData(verifyCodeResponse.getData());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        customType(VerifyCodeActivity.this,"left-to-right");
                        finish();
                    }
                    if (verifyCodeIntent.equals("reset_password"))
                    {
                        Intent intent = new Intent(getApplicationContext(), NewPasswordActivity.class);
                        intent.putExtra(Codes.RECOVERY_EMAIL, recoveryEmail);
                        startActivity(intent);
                        customType(VerifyCodeActivity.this,"left-to-right");
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(VerifyCodeActivity.this, "" + verifyCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.resendcode_textview)
    void resendTextView(View v)
    {
        viewModel.sendCode(recoveryEmail).observe(this, new Observer<SendCodeResponse>()
        {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse)
            {
                if (sendCodeResponse.getStatus() == 200)
                {
                    Toast.makeText(VerifyCodeActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(VerifyCodeActivity.this, "" + sendCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
