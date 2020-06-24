package com.buildyourevent.buildyourevent.user.auth.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.buildyourevent.buildyourevent.database.InterfaceApi;
import com.buildyourevent.buildyourevent.database.RetrofitClient;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordRequest;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordResponse;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.code.VerifyCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.auth.resetpassword.ResetPasswordResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyRepo
{
    MutableLiveData<SendCodeResponse> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<VerifyCodeResponse> verifyLivedata = new MutableLiveData<>();
    MutableLiveData<ResetPasswordResponse> resetPassLiveData = new MutableLiveData<>();


    InterfaceApi interfaceApi;

    public VerifyRepo()
    {
        interfaceApi = RetrofitClient.getApiClient(Codes.AUTH_BASE_URL).create(InterfaceApi.class);
    }

    public MutableLiveData<SendCodeResponse> sendCode(String email)
    {
        Call<SendCodeResponse> responseCall = interfaceApi.sendCode(email);
        responseCall.enqueue(new Callback<SendCodeResponse>()
        {
            @Override
            public void onResponse(Call<SendCodeResponse> call, Response<SendCodeResponse> response)
            {
                if (response.isSuccessful())
                {
                    mutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<SendCodeResponse> call, Throwable t)
            {
            }
        });

        return mutableLiveData;
    }

    public MutableLiveData<VerifyCodeResponse> verifyCode(String email, int code)
    {
        Call<VerifyCodeResponse> responseCall = interfaceApi.verifyCode(email, code);
        responseCall.enqueue(new Callback<VerifyCodeResponse>()
        {
            @Override
            public void onResponse(Call<VerifyCodeResponse> call, Response<VerifyCodeResponse> response)
            {
                if (response.isSuccessful())
                {
                    verifyLivedata.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<VerifyCodeResponse> call, Throwable t)
            {
            }
        });

        return verifyLivedata;
    }

    public MutableLiveData<ResetPasswordResponse> resetPassword(String email, String password)
    {
        Call<ResetPasswordResponse> responseCall = interfaceApi.resetPassword(email, password);
        responseCall.enqueue(new Callback<ResetPasswordResponse>()
        {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response)
            {
                if (response.isSuccessful())
                {
                    resetPassLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t)
            {
            }
        });

        return resetPassLiveData;
    }

/*
    public MutableLiveData<ResetPasswordResponse> resetPassword(String email, String password)
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

    public MutableLiveData<VerifyCodeResponse> verifyCode(String email, int code)
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

    public MutableLiveData<ChangePasswordResponse> changePassword(ChangePasswordRequest changePasswordRequest)
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
    }*/

}
