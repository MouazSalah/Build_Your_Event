package com.buildyourevent.buildyourevent.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.ui.auth.LoginActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment
{
    @BindView(R.id.nameprofile_textview1) TextView userNameTextView1;
    @BindView(R.id.nameprofile_textview2) TextView userNameTextView2;
    @BindView(R.id.userprofile_imageview) CircleImageView profileImageView;
    @BindView(R.id.emailprofile_textview) TextView emailTextView;
    @BindView(R.id.countryprofile_textview) TextView countryTextView;
    @BindView(R.id.cityprofile_textview) TextView cityTextView;
    @BindView(R.id.mobileprofile_textview) TextView mobileTextView;

    @BindView(R.id.notlogin_layout) LinearLayout notLoginLayout;
    @BindView(R.id.profiledata_layout) LinearLayout profileLayout;

    SharedPrefMethods prefMethods;
    UserData userData;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
         View root = inflater.inflate(R.layout.fragment_profile, container, false);
         ButterKnife.bind(this, root);

         prefMethods = new SharedPrefMethods(getActivity());
         userData = prefMethods.getUserData();

         if (userData == null)
         {
             notLoginLayout.setVisibility(View.VISIBLE);
             profileLayout.setVisibility(View.GONE);
         }
         else
         {
             setDataToViews();
         }

        return root;
    }


    @OnClick(R.id.loginnow_button)
    void loginNow(View v)
    {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


    private void setDataToViews()
    {
        userNameTextView1.setText(userData.getName());
        userNameTextView2.setText(userData.getName());
        emailTextView.setText(userData.getEmail());
        countryTextView.setText(userData.getCountryName());
        cityTextView.setText(userData.getCityName());
        mobileTextView.setText("" + userData.getMobile());

    }
}
