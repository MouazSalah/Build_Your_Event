package com.buildyourevent.buildyourevent.user.auth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.AuthFragmentConditionsBinding;

public class ConditionFragment extends Fragment
{
    AuthFragmentConditionsBinding binding;
    NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = AuthFragmentConditionsBinding.inflate(inflater, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.auth_navigation);

        binding.btnAcceptTerms.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.conditions_to_register);
            }
        });
        return binding.getRoot();
    }
}

       /* viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        viewModel.getAllCountries().observe(this, new Observer<List<CountryData>>() {
            @Override
            public void onChanged(List<CountryData> countryData) {
                countriesList = countryData;
                buildCountriesSpinner();
            }
        });

        return binding.getRoot();
    }

    private void buildCountriesSpinner() {
        CountriesAdapter countriesAdapter = new CountriesAdapter(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, countriesList);
        countrySpinner.setAdapter(countriesAdapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                Log.e(TAG, "onItemSelected: " + countriesList.get(pos).getId());
                countryId = countriesList.get(pos).getId();
                getCities(countriesList.get(pos).getId());
                Log.d(Codes.APP_TAGS, "countries size: " + countriesList.size());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCities(int countryId) {
        Log.d(Codes.APP_TAGS, "get cities method");
        viewModel.getAllCities(countryId).observe(this, new Observer<List<CityData>>() {
            @Override
            public void onChanged(List<CityData> cityData) {
                Log.d(Codes.APP_TAGS, "cities size: " + cityData.size());
                citiesList = cityData;
                buildCitiesSpinner();
                Log.d(Codes.APP_TAGS, "size: " + citiesList.size());
            }
        });
    }

    public void buildCitiesSpinner() {
        CitiesAdapter citiesAdapter = new CitiesAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1, citiesList);
        citySpinner.setAdapter(citiesAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                Log.e(TAG, "onItemSelected: " + citiesList.get(pos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
*/