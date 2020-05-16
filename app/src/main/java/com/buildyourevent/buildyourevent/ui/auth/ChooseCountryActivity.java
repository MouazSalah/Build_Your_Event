package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ChooseCountryActivity extends AppCompatActivity
{
    UserViewModel viewModel;

    @BindView(R.id.skip_country_spinner) Spinner countrySpinner;
    @BindView(R.id.skip_city_spinner) Spinner citySpinner;

    List<CountryData> countriesList = new ArrayList<>();
    List<CityData> citiesList = new ArrayList<>();
    SharedPrefMethods prefMethods;
    int countryId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
         prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_country);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        viewModel.getAllCountries().observe(this, new Observer<List<CountryData>>() {
            @Override
            public void onChanged(List<CountryData> countryData) {
                countriesList = countryData;
                buildCountriesSpinner();
            }
        });
    }

    private void buildCountriesSpinner()
    {
        CountriesAdapter countriesAdapter = new CountriesAdapter(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, countriesList);
        countrySpinner.setAdapter(countriesAdapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                Log.e(TAG, "onItemSelected: " + countriesList.get(pos).getId());
                countryId = countriesList.get(pos).getId();
                getCities(countriesList.get(pos).getId());
                Log.d(Codes.APP_TAGS, "countries size: " +  countriesList.size());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void getCities(int countryId)
    {
        Log.d(Codes.APP_TAGS, "get cities method");
        viewModel.getAllCities(countryId).observe(this, new Observer<List<CityData>>()
        {
            @Override
            public void onChanged(List<CityData> cityData)
            {
                Log.d(Codes.APP_TAGS, "cities size: " +  cityData.size());
                citiesList = cityData;
                buildCitiesSpinner();
                Log.d(Codes.APP_TAGS, "size: " +  citiesList.size());
            }
        });
    }

    public void buildCitiesSpinner()
    {
        CitiesAdapter citiesAdapter = new CitiesAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1, citiesList);
        citySpinner.setAdapter(citiesAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                Log.e(TAG, "onItemSelected: " + citiesList.get(pos).getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }


    @OnClick(R.id.skip_choosecountry_skibbtn)
    void enterApp(View v)
    {
        prefMethods.saveCountryId(countryId);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

}
