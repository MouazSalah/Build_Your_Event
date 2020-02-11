/*
package com.buildyourevent.buildyourevent.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordRequest;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordResponse;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.code.VerifyCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.login.LoginRequest;
import com.buildyourevent.buildyourevent.model.auth.login.LoginResponse;
import com.buildyourevent.buildyourevent.model.auth.logout.LogoutRequest;
import com.buildyourevent.buildyourevent.model.auth.logout.LogoutResponse;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.auth.resetpassword.ResetPasswordResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AuthViewModel extends ViewModel implements Observer<Object>
{
    private static String TAG = "LoginViewModelTags";
    private AuthRepository authRepository = new AuthRepository();
    private MutableLiveData<Object> viewModelLiveData = new MutableLiveData<>();

    public AuthViewModel() {
        authRepository.getMutableLiveData().observeForever(this);
    }


    public LiveData<LoginResponse> loginCurrentUser(LoginRequest loginRequest)
    {
        return authRepository.loginUser(loginRequest);
    }

    public LiveData<RegisterResponse> registerUser(MultipartBody.Part pic, RequestBody toString, RequestBody toString1, RequestBody toString2,
                                                   RequestBody toString3, RequestBody countryId, RequestBody cityId)
    {
        return authRepository.registerNewUser(pic, toString,  toString1,  toString2,
                             toString3,  countryId,  cityId);
    }

    public LiveData<List<CountryData>> getAllCountries()
    {
        return authRepository.getCountriesMutableLiveData();
    }

    public LiveData<List<CityData>> getAllCities(int countryId)
    {
        return authRepository.getCitiesMutableLiveData(countryId);
    }

    public LiveData<LogoutResponse> logoutUser(LogoutRequest logoutRequest)
    {
        return authRepository.logoutUser(logoutRequest);
    }

    public LiveData<SendCodeResponse> sendCode(String email)
    {
        return authRepository.getSendCodeMutableLiveData(email);
    }

    public LiveData<VerifyCodeResponse> verifyCode(String email, int code)
    {
        return authRepository.getVerifyCodeResponseMutableLiveData(email, code);
    }

    public LiveData<ResetPasswordResponse> resetPassword(String email, String password)
    {
        return authRepository.getResetPasswordResponseMutableLiveData(email, password);
    }

    public LiveData<ChangePasswordResponse> changePassword(ChangePasswordRequest changePasswordRequest)
    {
        return authRepository.getChangePasswordMutableLiveData(changePasswordRequest);
    }

    @Override
    public void onChanged(Object o)
    {
        viewModelLiveData.setValue(o);
    }


}

*/
