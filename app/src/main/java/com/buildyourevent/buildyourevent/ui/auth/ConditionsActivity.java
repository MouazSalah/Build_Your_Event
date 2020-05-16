package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;

import butterknife.BindView;

public class ConditionsActivity extends AppCompatActivity
{
    TextView tvAgree;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);

        email = getIntent().getStringExtra(Codes.RECOVERY_EMAIL);

        tvAgree = findViewById(R.id.btn_acceptTerms);
        tvAgree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), RegisterVerifyCodeActivity.class);
                intent.putExtra(Codes.RECOVERY_EMAIL, email);
                startActivity(intent);
            }
        });

    }
}
