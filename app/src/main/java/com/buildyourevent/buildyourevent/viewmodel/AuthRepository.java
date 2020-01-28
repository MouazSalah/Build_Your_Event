package com.buildyourevent.buildyourevent.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.buildyourevent.buildyourevent.database.InterfaceApi;
import com.buildyourevent.buildyourevent.database.RetrofitClient;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordRequest;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordResponse;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.cities.CityResponse;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.code.VerifyCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryResponse;
import com.buildyourevent.buildyourevent.model.auth.login.LoginRequest;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.auth.login.LoginResponse;
import com.buildyourevent.buildyourevent.model.auth.logout.LogoutRequest;
import com.buildyourevent.buildyourevent.model.auth.logout.LogoutResponse;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterRequest;
import com.buildyourevent.buildyourevent.model.auth.resetpassword.ResetPasswordResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository
{
    InterfaceApi interfaceApi;
    MutableLiveData<Object> mutableLiveData = new MutableLiveData<>();

    private MutableLiveData<List<CountryData>> countriesMutableLiveData = new MutableLiveData<>();
    private ArrayList<CountryData> countriesList = new ArrayList<>();

    private MutableLiveData<List<CityData>> citiesMutableLiveData = new MutableLiveData<>();
    private ArrayList<CityData> citiesList = new ArrayList<>();

    private MutableLiveData<LogoutResponse> logoutMutableLiveData = new MutableLiveData<>();
    private LogoutResponse logoutResponse = new LogoutResponse();

    private MutableLiveData<RegisterResponse> registerResponseMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<SendCodeResponse> sendCodeMutableLiveData = new MutableLiveData<>();
    private SendCodeResponse sendCodeResponse = new SendCodeResponse();

    private MutableLiveData<VerifyCodeResponse> verifyCodeResponseMutableLiveData = new MutableLiveData<>();
    private VerifyCodeResponse verifyCodeResponse = new VerifyCodeResponse();

    private MutableLiveData<ResetPasswordResponse> resetPasswordResponseMutableLiveData = new MutableLiveData<>();
    private ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();

    private MutableLiveData<ChangePasswordResponse> changePasswordResponseMutableLiveData = new MutableLiveData<>();

    public AuthRepository() { interfaceApi = RetrofitClient.getApiClient(Codes.AUTH_BASE_URL).create(InterfaceApi.class); }

    public MutableLiveData<Object> getMutableLiveData() {
        return mutableLiveData;
    }

    public MutableLiveData<LoginResponse> loginUser(LoginRequest loginRequest)
    {
        Call<LoginResponse> loginUser = interfaceApi.loginToAccount(loginRequest);
        loginUser.enqueue(new Callback<LoginResponse>()
        {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                if (response.isSuccessful())
                {
                    loginResponseMutableLiveData.setValue(response.body());
                }
                else
                {
                    Log.d(Codes.APP_TAGS, "something happened, try again");
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("retrfitcallback", "failure" + t.getMessage());
            }
        });
        return loginResponseMutableLiveData;
    }

    public MutableLiveData<RegisterResponse> registerNewUser(RegisterRequest registerRequest)
    {
        Call<RegisterResponse> responseCall = interfaceApi.createNewUser(registerRequest);
        responseCall.enqueue(new Callback<RegisterResponse>()
        {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "register repo not success:/ " + response.message());
                }
                else
                {
                    Log.d(Codes.APP_TAGS, "register repo done" + response.message());
                    Log.d(Codes.APP_TAGS, "register repo done" + response.body().getStatus());
                    registerResponseMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "register failed" + t.getMessage());
            }
        });

        return registerResponseMutableLiveData;
    }


    public MutableLiveData<List<CountryData>> getCountriesMutableLiveData()
    {
        Call<CountryResponse> responseCall = interfaceApi.getAllCountries();
        responseCall.enqueue(new Callback<CountryResponse>()
        {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "countries not success" + response.message());
                }
                else
                {
                    CountryResponse countryResponse = response.body();
                    Log.d(Codes.APP_TAGS, "rep countries sizes: " + response.body().getData().size());
                    if (countryResponse != null && countryResponse.getData() != null)
                    {
                        countriesList = (ArrayList<CountryData>) countryResponse.getData();
                        countriesMutableLiveData.setValue(countriesList);
                        Log.d(Codes.APP_TAGS, "rep category size: " + response.body().getData().size());
                    }
                }
            }
            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "category failed" + t.getMessage());
            }
        });

        return countriesMutableLiveData;
    }


    public MutableLiveData<List<CityData>> getCitiesMutableLiveData(int countryId)
    {
        Call<CityResponse> responseCall = interfaceApi.getAllCities(countryId);
        responseCall.enqueue(new Callback<CityResponse>()
        {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "countries not success" + response.message());
                }
                else
                {
                    CityResponse cityResponse = response.body();
                    Log.d(Codes.APP_TAGS, "rep countries sizes: " + response.body().getData().size());
                    if (cityResponse != null && cityResponse.getData() != null)
                    {
                        citiesList = (ArrayList<CityData>) cityResponse.getData();
                        citiesMutableLiveData.setValue(citiesList);
                        Log.d(Codes.APP_TAGS, "rep category size: " + response.body().getData().size());
                    }
                }
            }
            @Override
            public void onFailure(Call<CityResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "category failed" + t.getMessage());
            }
        });

        return citiesMutableLiveData;
    }


    public MutableLiveData<SendCodeResponse> getSendCodeMutableLiveData(String email)
    {
        Call<SendCodeResponse> responseCall = interfaceApi.sendCode(email);
        responseCall.enqueue(new Callback<SendCodeResponse>()
        {
            @Override
            public void onResponse(Call<SendCodeResponse> call, Response<SendCodeResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "send code not success" + response.message());
                }
                else
                {
                    sendCodeResponse = response.body();
                    sendCodeMutableLiveData.setValue(sendCodeResponse);
                }
            }
            @Override
            public void onFailure(Call<SendCodeResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "send code failed" + t.getMessage());
            }
        });

        return sendCodeMutableLiveData;
    }


    public MutableLiveData<ResetPasswordResponse> getResetPasswordResponseMutableLiveData(String email, String password)
    {
        Call<ResetPasswordResponse> responseCall = interfaceApi.resetPassword(email, password);
        responseCall.enqueue(new Callback<ResetPasswordResponse>()
        {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "reset password not success" + response.message());
                }
                else
                {
                    resetPasswordResponse = response.body();
                    resetPasswordResponseMutableLiveData.setValue(resetPasswordResponse);
                }
            }
            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "reset password  failed" + t.getMessage());
            }
        });

        return resetPasswordResponseMutableLiveData;
    }

    public MutableLiveData<VerifyCodeResponse> getVerifyCodeResponseMutableLiveData(String email, int code)
    {
        Call<VerifyCodeResponse> responseCall = interfaceApi.verifyCode(email, code);
        responseCall.enqueue(new Callback<VerifyCodeResponse>()
        {
            @Override
            public void onResponse(Call<VerifyCodeResponse> call, Response<VerifyCodeResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "verify code not success" + response.message());
                }
                else
                {
                    verifyCodeResponse = response.body();
                    verifyCodeResponseMutableLiveData.setValue(verifyCodeResponse);
                }
            }
            @Override
            public void onFailure(Call<VerifyCodeResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "verify code failed" + t.getMessage());
            }
        });

        return verifyCodeResponseMutableLiveData;
    }


    public MutableLiveData<ChangePasswordResponse> getChangePasswordMutableLiveData(ChangePasswordRequest changePasswordRequest)
    {
        Call<ChangePasswordResponse> responseCall = interfaceApi.changePassword(changePasswordRequest);
        responseCall.enqueue(new Callback<ChangePasswordResponse>()
        {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "verify code not success" + response.message());
                }
                else
                {
                    changePasswordResponseMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "verify code failed" + t.getMessage());
            }
        });

        return changePasswordResponseMutableLiveData;
    }



    public MutableLiveData<LogoutResponse> logoutUser(LogoutRequest logoutRequest)
    {
        Call<LogoutResponse> logoutCall = interfaceApi.logout(logoutRequest);
        logoutCall.enqueue(new Callback<LogoutResponse>()
        {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "countries not success" + response.message());
                }
                else
                {
                    logoutResponse = response.body();
                    logoutMutableLiveData.setValue(logoutResponse);

                }
            }
            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "category failed" + t.getMessage());
            }
        });

        return logoutMutableLiveData;
    }
}
