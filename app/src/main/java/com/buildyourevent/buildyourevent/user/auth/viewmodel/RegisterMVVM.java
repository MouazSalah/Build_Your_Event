package com.buildyourevent.buildyourevent.user.auth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.user.auth.repository.RegisterRepo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterMVVM extends ViewModel
{
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<Integer> countryId = new MutableLiveData<>();
    public MutableLiveData<Integer> cityId = new MutableLiveData<>();

    private MutableLiveData<Integer> clickLiveData;
    RegisterRepo registerRepo = new RegisterRepo();

    public MutableLiveData<Integer> getMutableLiveData()
    {
        if (clickLiveData == null)
        {
            clickLiveData = new MutableLiveData<>();
        }
        return clickLiveData;
    }

    public void onRegisterClicked()
    {
        clickLiveData.setValue(1);
    }

    public void onLoginClicked()
    {
        clickLiveData.setValue(2);
    }

    public void onReadClicked()
    {
        clickLiveData.setValue(3);
    }

    public LiveData<RegisterResponse> registerUser(MultipartBody.Part pic, RequestBody toString, RequestBody toString1, RequestBody toString2,
                                                   RequestBody toString3, RequestBody countryId, RequestBody cityId)
    {
        return registerRepo.createAccount(pic, toString,  toString1,  toString2,
                toString3,  countryId,  cityId);
    }

    public LiveData<List<CountryData>> getAllCountries()
    {
        return registerRepo.getCountries();
    }

    public LiveData<List<CityData>> getAllCities(int countryId)
    {
        return registerRepo.getCities(countryId);
    }

    public LiveData<SendCodeResponse> sendCode(String email)
    {
        return registerRepo.sendCode(email);
    }

}
