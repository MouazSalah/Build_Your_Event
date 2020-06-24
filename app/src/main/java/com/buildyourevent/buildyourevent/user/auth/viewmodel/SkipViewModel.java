package com.buildyourevent.buildyourevent.user.auth.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buildyourevent.buildyourevent.database.AuthRepository;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SkipViewModel extends ViewModel {
    AuthRepository authRepository = new AuthRepository();
    //    LiveData<List<CountryData>> countriesList = new MutableLiveData<>();
//    MutableLiveData<List<CityData>> citiesList = new MutableLiveData<>();
    public ObservableList<CountryData> countryList = new ObservableArrayList<>();
    public MutableLiveData<CountryData> getData = new MutableLiveData<>();

    int countryId, cityId;

    public SkipViewModel() {
//        countriesList = getAllCountries();
        List<CountryData> ll = new ArrayList<>();

        getData.observeForever(item->{
            Timber.e(item.getId()+"");
        });
    }

    public void onSkipClicked() {

    }

    public LiveData<List<CountryData>> getAllCountries() {
        return authRepository.getCountriesMutableLiveData();
    }

    public LiveData<List<CityData>> getAllCities(int countryId) {
        return authRepository.getCitiesMutableLiveData(countryId);
    }

    public void onCountrySpinnerClickec(AdapterView<?> parent, View view, int pos, long id) {
//       citiesList = getAllCities(parent.getId());
        countryId = parent.getId();
        Log.d("Mouaz country id : ", +parent.getId() + "");
    }

    public void onCitySpinnerClickec(AdapterView<?> parent, View view, int pos, long id) {
        cityId = parent.getId();
        Log.d("Mouaz city id : ", +parent.getId() + "");

    }
}
