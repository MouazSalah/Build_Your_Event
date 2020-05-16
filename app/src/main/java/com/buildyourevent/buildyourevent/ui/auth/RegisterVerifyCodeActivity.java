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
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.code.VerifyCodeResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;

public class RegisterVerifyCodeActivity extends AppCompatActivity
{
    @BindView(R.id.et_registercode) EditText etCode;
    @BindView(R.id.btn_confirm) TextView tvCodeText;
    @BindView(R.id.verifycode_text) TextView tvCode;

    @BindView(R.id.verfiyregister_progressbar)
    ProgressBar verifyCodeProgressBar;

    String recoveryEmail;
    UserViewModel viewModel;
    SharedPrefMethods prefMethods;

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
        setContentView(R.layout.activity_register_verify_code);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        recoveryEmail = getIntent().getStringExtra(Codes.RECOVERY_EMAIL);

        tvCode.append( " "  +recoveryEmail);
    }

    @OnClick(R.id.btn_confirm)
    void confirmVerification(View v)
    {
        if (!etCode.getText().toString().isEmpty())
        {
            verifyCode();
        }
    }

    private void verifyCode()
    {
        verifyCodeProgressBar.setVisibility(View.VISIBLE);
        viewModel.verifyCode(recoveryEmail, Integer.parseInt(etCode.getText().toString())).observe(this, new Observer<VerifyCodeResponse>() {
            @Override
            public void onChanged(VerifyCodeResponse verifyCodeResponse)
            {
                if (verifyCodeResponse.getStatus() == 201)
                {
                    verifyCodeProgressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra(Codes.RECOVERY_EMAIL, recoveryEmail);
                    startActivity(intent);
                    customType(RegisterVerifyCodeActivity.this,"left-to-right");
                    finish();
                }
                else
                {
                    verifyCodeProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterVerifyCodeActivity.this, "" + verifyCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.btn_resendCode)
    void resendTextView(View v)
    {
        viewModel.sendCode(recoveryEmail).observe(this, new Observer<SendCodeResponse>()
        {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse)
            {
                if (sendCodeResponse.getStatus() == 200)
                {
                    Toast.makeText(RegisterVerifyCodeActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RegisterVerifyCodeActivity.this, "" + sendCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
