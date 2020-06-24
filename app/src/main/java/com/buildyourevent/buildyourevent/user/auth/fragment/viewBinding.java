package com.buildyourevent.buildyourevent.user.auth.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.ui.auth.CountriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class viewBinding
{
    @BindingAdapter(value = {"app:getData"})
    public static void bindRecycler(Spinner spinner, MutableLiveData<CountryData> returnData){
        ObservableList<CountryData>serverData = new ObservableArrayList<>();
        for (int i = 0; i < 5; i++) {
            CountryData data = new CountryData();
            data.setId(i + 1);
            data.setCountryName("mado" + i);
            serverData.add(i,data);
        }
//        List<CountryData> mm = new ArrayList<>(serverData);
        CountriesAdapter countriesAdapter = new CountriesAdapter(spinner.getContext(), serverData);
        spinner.setAdapter(countriesAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
//                countryId = countriesList.get(pos).getId();
//                getCities(countriesList.get(pos).getId());

//                returnData.setValue(serverData.get(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
