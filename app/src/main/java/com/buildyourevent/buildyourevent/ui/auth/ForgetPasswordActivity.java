package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.viewmodel.AuthViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;

public class ForgetPasswordActivity extends AppCompatActivity
{
    @BindView(R.id.forgetpassword_email)
    EditText etEmail;
    AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
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
        viewModel.sendCode(etEmail.getText().toString()).observe(this, new Observer<SendCodeResponse>()
        {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse)
            {
                if (sendCodeResponse.getStatus() == 200)
                {
                    Intent intent = new Intent(getApplicationContext(), VerifyCodeActivity.class);
                    intent.putExtra(Codes.RECOVERY_EMAIL, etEmail.getText().toString());
                    intent.putExtra(Codes.VERIFY_CODE_INTENT, "reset_password");
                    startActivity(intent);
                    customType(ForgetPasswordActivity.this,"left-to-right");
                    finish();
                }
                else
                {
                    Toast.makeText(ForgetPasswordActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
