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
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static maes.tech.intentanim.CustomIntent.customType;

public class VerifyCodeActivity extends AppCompatActivity
{
    @BindView(R.id.verifycode_code) EditText etCode;
    @BindView(R.id.verifycode_text) TextView tvCodeText;
    @BindView(R.id.verifycode_progressBar)
    ProgressBar verifyCodeProgressBar;

    String recoveryEmail;
    String verifyCodeIntent;
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
        setContentView(R.layout.activity_verify_code);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

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
        verifyCodeProgressBar.setVisibility(View.VISIBLE);
        viewModel.verifyCode(recoveryEmail, Integer.parseInt(etCode.getText().toString())).observe(this, new Observer<VerifyCodeResponse>() {
            @Override
            public void onChanged(VerifyCodeResponse verifyCodeResponse)
            {
                if (verifyCodeResponse.getStatus() == 201)
                {
                    if (verifyCodeIntent.equals("register"))
                    {
                         // UserData userData =  verifyCodeResponse.getData();
                         // prefMethods.SaveUserData(verifyCodeResponse.getData());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        customType(VerifyCodeActivity.this,"left-to-right");
                        finish();
                        verifyCodeProgressBar.setVisibility(View.GONE);
                    }
                    if (verifyCodeIntent.equals("reset_password"))
                    {
                        verifyCodeProgressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), NewPasswordActivity.class);
                        intent.putExtra(Codes.RECOVERY_EMAIL, recoveryEmail);
                        startActivity(intent);
                        customType(VerifyCodeActivity.this,"left-to-right");
                        finish();
                    }
                }
                else
                {
                    verifyCodeProgressBar.setVisibility(View.GONE);
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
