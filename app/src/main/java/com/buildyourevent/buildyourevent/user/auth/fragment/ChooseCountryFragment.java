package com.buildyourevent.buildyourevent.user.auth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.buildyourevent.buildyourevent.databinding.AuthFragmentCountryBinding;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.ui.auth.CitiesAdapter;
import com.buildyourevent.buildyourevent.ui.auth.CountriesAdapter;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.user.auth.viewmodel.SkipViewModel;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ChooseCountryFragment extends Fragment
{
    AuthFragmentCountryBinding binding;
    SkipViewModel viewModel;

    SharedPrefMethods prefMethods;
    int countryId, cityId;
    List<CountryData> countriesList = new ArrayList<>();
    List<CityData> citiesList = new ArrayList<>();
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AuthFragmentCountryBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(SkipViewModel.class);


        viewModel.getAllCountries().observe(requireActivity(), new Observer<List<CountryData>>()
        {
            @Override
            public void onChanged(List<CountryData> countryData) {
                countriesList = countryData;
               // buildCountriesSpinner();
            }
        });

        binding.btnSkip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return binding.getRoot();

    }

    private void buildCountriesSpinner() {
//        CountriesAdapter countriesAdapter = new CountriesAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, countriesList);
//        binding.spinCountry.setAdapter(countriesAdapter);
//        binding.spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
//                countryId = countriesList.get(pos).getId();
//                getCities(countriesList.get(pos).getId());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    private void getCities(int countryId) {
        viewModel.getAllCities(countryId).observe(getActivity(), new Observer<List<CityData>>()
        {
            @Override
            public void onChanged(List<CityData> cityData)
            {
                citiesList = cityData;
                buildCitiesSpinner();
            }
        });
    }

    public void buildCitiesSpinner()
    {
        CitiesAdapter citiesAdapter = new CitiesAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, citiesList);
        binding.spinCity.setAdapter(citiesAdapter);
        binding.spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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

}
