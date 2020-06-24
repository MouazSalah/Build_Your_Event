package com.buildyourevent.buildyourevent.user.auth.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.buildyourevent.buildyourevent.database.InterfaceApi;
import com.buildyourevent.buildyourevent.database.RetrofitClient;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.cities.CityResponse;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryResponse;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepo
{
    InterfaceApi interfaceApi;
    MutableLiveData<RegisterResponse> registerLiveData = new MutableLiveData<>();
    MutableLiveData<List<CountryData>> countryLiveData = new MutableLiveData<>();
    MutableLiveData<List<CityData>> cityLiveData = new MutableLiveData<>();
    MutableLiveData<SendCodeResponse> codeLiveData = new MutableLiveData<>();


    public RegisterRepo()
    {
        interfaceApi = RetrofitClient.getApiClient(Codes.AUTH_BASE_URL).create(InterfaceApi.class);
    }


    public MutableLiveData<RegisterResponse> createAccount(MultipartBody.Part pic, RequestBody toString, RequestBody toString1, RequestBody toString2,
                                                             RequestBody toString3, RequestBody countryId, RequestBody cityId)
    {
        Call<RegisterResponse> responseCall = interfaceApi.createNewUser(pic, toString,  toString1,  toString2,
                                          toString3,  countryId,  cityId);
        responseCall.enqueue(new Callback<RegisterResponse>()
        {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response)
            {
                if (response.isSuccessful())
                {
                    registerLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t)
            {
            }
        });

        return registerLiveData;
    }

    public MutableLiveData<List<CountryData>> getCountries()
    {
        Call<CountryResponse> responseCall = interfaceApi.getAllCountries();
        responseCall.enqueue(new Callback<CountryResponse>()
        {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null && response.body().getData() != null)
                    {
                        countryLiveData.setValue(response.body().getData());
                    }
                }
            }
            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t)
            {
            }
        });

        return countryLiveData;
    }

    public MutableLiveData<List<CityData>> getCities(int countryId)
    {
        Call<CityResponse> responseCall = interfaceApi.getAllCities(countryId);
        responseCall.enqueue(new Callback<CityResponse>()
        {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null && response.body().getData() != null)
                    {
                        cityLiveData.setValue(response.body().getData());
                    }
                }
            }
            @Override
            public void onFailure(Call<CityResponse> call, Throwable t)
            { }
        });

        return cityLiveData;
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
                    codeLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<SendCodeResponse> call, Throwable t)
            {
            }
        });

        return codeLiveData;
    }


}
