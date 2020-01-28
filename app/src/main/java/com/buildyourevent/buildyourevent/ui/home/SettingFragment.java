package com.buildyourevent.buildyourevent.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordRequest;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.auth.logout.LogoutRequest;
import com.buildyourevent.buildyourevent.ui.auth.LoginActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.AuthViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static maes.tech.intentanim.CustomIntent.customType;


public class SettingFragment extends Fragment
{
   /* final String[] Options = {getString(R.string.arabic), getString(R.string.english)};
    AlertDialog.Builder langDialog;*/

   AuthViewModel viewModel;


   @BindView(R.id.settings_layout) LinearLayout settingLayout;
   @BindView(R.id.notlogin_layout) LinearLayout notLoginLayout;
   @BindView(R.id.changepassword_layout) LinearLayout changePasswordLayout;

    @BindView(R.id.change_password_textview) TextView showChangePassDialog;

    EditText oldPasswordEditText ;
    EditText newPasswordEditText ;
    EditText confirmPasswordEditText;
    TextView cancelChangePasswordBtn ;
    TextView confirmChangePasswordBtn ;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    SharedPrefMethods prefMethods;
    UserData userData;

    final String[] Options = {"Arabic", "English"};
    AlertDialog.Builder langDialog;

    String languageToLoad ;
    String preferedLanguage;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, root);

        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        prefMethods = new SharedPrefMethods(getActivity());
        preferedLanguage = prefMethods.getUserLanguage();

        userData = prefMethods.getUserData();
        if (userData == null)
        {
            notLoginLayout.setVisibility(View.VISIBLE);
            settingLayout.setVisibility(View.GONE);
        }
       // langDialog = new AlertDialog.Builder(getActivity());
        
        return root;
    }


    @OnClick(R.id.chooselanguage_textview)
    void changeLanguageButton()
    {
        Log.d(Codes.APP_TAGS, "alert dialog");
        String[] dialogLanguages = {"Arabic", "English"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a language");
        builder.setItems(dialogLanguages, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (which == 0)
                {
                    languageToLoad = "ar";
                    Toast.makeText(getActivity(), "Arabic Selected", Toast.LENGTH_SHORT).show();
                }
                if (which == 1)
                {
                    languageToLoad = "en";
                    Toast.makeText(getActivity(), "English Selected", Toast.LENGTH_SHORT).show();
                }

                changeAppLanguage();
            }
        });
        builder.show();
    }


    @OnClick(R.id.change_password_textview)
    void showChangePasswordDialog(View v)
    {
        changePasswordLayout.setVisibility(View.VISIBLE);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_password_layout, null);

        oldPasswordEditText = (EditText) dialogView.findViewById(R.id.oldpassword_edittext);
        newPasswordEditText = (EditText) dialogView.findViewById(R.id.newpassword_edittext);
        confirmPasswordEditText = (EditText) dialogView.findViewById(R.id.confirmpassword_edittext);
        cancelChangePasswordBtn = (TextView) dialogView.findViewById(R.id.cancel_change_password_button);
        confirmChangePasswordBtn = (TextView) dialogView.findViewById(R.id.confirm_change_password_button);

        cancelChangePasswordBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                alertDialog.hide();
            }
        });
        confirmChangePasswordBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                confirmChangePasswordTask();
            }
        });


        dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.show();

         oldPasswordEditText = (EditText) dialogView.findViewById(R.id.oldpassword_edittext);
         newPasswordEditText = (EditText) dialogView.findViewById(R.id.newpassword_edittext);
         confirmPasswordEditText = (EditText) dialogView.findViewById(R.id.confirmpassword_edittext);
         cancelChangePasswordBtn = (TextView) dialogView.findViewById(R.id.cancel_change_password_button);
         confirmChangePasswordBtn = (TextView) dialogView.findViewById(R.id.confirm_change_password_button);

         cancelChangePasswordBtn.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view)
             {
                 alertDialog.hide();
             }
         });
        confirmChangePasswordBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                confirmChangePasswordTask();
            }
        });
    }


    void confirmChangePasswordTask()
    {
        if (oldPasswordEditText.getText().toString().isEmpty())
        {
            oldPasswordEditText.setError("enter old password");
        }
        else if (newPasswordEditText.getText().toString().isEmpty())
        {
            newPasswordEditText.setError("enter new password");
        }
        else if (confirmPasswordEditText.getText().toString().isEmpty())
        {
            confirmPasswordEditText.setError("enter password again");
        }
        else if (newPasswordEditText.getText().toString().length() < 6)
        {
            newPasswordEditText.setError("short password");
        }
        else if (!confirmPasswordEditText.getText().toString().equals(newPasswordEditText.getText().toString()))
        {
            confirmPasswordEditText.setError("password not match");
        }
        else
        {
            changePasswordTask();
        }
    }

    private void changePasswordTask()
    {
        String email = "mouazsalah627@gmail.com";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(email, oldPasswordEditText.getText().toString(),
                    newPasswordEditText.getText().toString());

        viewModel.changePassword(changePasswordRequest).observe(this, new Observer<ChangePasswordResponse>()
        {
            @Override
            public void onChanged(ChangePasswordResponse changePasswordResponse)
            {
                if (changePasswordResponse.getStatus() == 200)
                {
                    alertDialog.dismiss();
                    Toast.makeText(getActivity(), "Changed Successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "" + changePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void changeAppLanguage()
    {
        prefMethods.saveUserLanguage(languageToLoad);
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(intent);
        customType(getActivity(),"fadein-to-fadeout");
        getActivity().finish();
    }

    @OnClick(R.id.log_out_textview)
    void logoutTask(View v)
    {
        prefMethods.deleteUserData();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
        customType(getActivity(),"left-to-right");
        getActivity().finish();
    }

    @OnClick(R.id.loginnow_button)
    void LoginNow(View v)
    {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        customType(getActivity(),"left-to-right");
        getActivity().finish();
    }

}