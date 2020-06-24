package com.buildyourevent.buildyourevent.user.auth.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.AuthFragmentRegisterBinding;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterRequest;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.ui.auth.CitiesAdapter;
import com.buildyourevent.buildyourevent.ui.auth.CountriesAdapter;
import com.buildyourevent.buildyourevent.user.auth.viewmodel.RegisterMVVM;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment
{
    AuthFragmentRegisterBinding binding;
    NavController navController ;
    SharedPrefMethods prefMethods;
    int countryId, cityId;
    static Bitmap bitmapPhoto;
    private int PICK_IMAGE_REQUEST = 1;
    MultipartBody.Part pic = null;
    File imageFile;
    List<CountryData> countriesList = new ArrayList<>();
    List<CityData> citiesList = new ArrayList<>();

    RegisterMVVM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = AuthFragmentRegisterBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterMVVM.class);
        binding.setModel(viewModel);
        navController = Navigation.findNavController(requireActivity(), R.id.auth_navigation);

        viewModel.getMutableLiveData().observe(requireActivity(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                if (integer == 1)
                {
                    checkAllFields();
                }
                if (integer == 2)
                {
                    navController.navigate(R.id.register_to_login);
                }
                if (integer == 3)
                {
                    navController.navigate(R.id.register_to_conditions);
                }
            }
        });


        viewModel.getAllCountries().observe(requireActivity(), new Observer<List<CountryData>>()
        {
            @Override
            public void onChanged(List<CountryData> countryData)
            {
                countriesList = countryData;
               // buildCountriesSpinner();
            }
        });

        binding.ivImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
            }
        });

        return binding.getRoot();
    }

    private void checkAllFields()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (binding.etName.getText().toString().isEmpty())
        {
            binding.etName.setError(getString(R.string.enter_name));
        } else if (binding.etEmail.getText().toString().isEmpty())
        {
            binding.etEmail.setError(getString(R.string.enter_email));
        } else if (binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError(getString(R.string.enter_password));
        } else if (!binding.etEmail.getText().toString().matches(emailPattern)) {
            binding.etEmail.setError(getString(R.string.invalid_email));
        } else if (binding.etPassword.getText().toString().trim().length() < 6) {
            binding.etPassword.setError(getString(R.string.short_password));
        } else if (binding.etMoile.getText().toString().isEmpty()) {
            binding.etMoile.setError(getString(R.string.enter_mobile));
        } else if (!binding.checkboxCondition.isChecked()) {
            Toast.makeText(getActivity(), getString(R.string.condition_toast), Toast.LENGTH_SHORT).show();
        } else if ( binding.spinCountry.getSelectedItem() == null) {
            Toast.makeText(getActivity(), getString(R.string.choose_country), Toast.LENGTH_SHORT).show();
        } else if ( binding.spinCity.getSelectedItem() == null) {
            Toast.makeText(getContext(), getString(R.string.choose_city), Toast.LENGTH_SHORT).show();
        } else {
            registerTask();
        }
    }

    public void registerTask()
    {
        binding.progressBar.setVisibility(View.VISIBLE);

        RegisterRequest userRequest = new RegisterRequest(binding.etName.getText().toString(),binding.etEmail.getText().toString() ,
                binding.etPassword.getText().toString(), binding.etMoile.getText().toString(), countryId, cityId, bitmapPhoto);

        RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), binding.etName.getText().toString());
        RequestBody user_email = RequestBody.create(MediaType.parse("text/plain"), binding.etEmail.getText().toString());
        RequestBody user_password = RequestBody.create(MediaType.parse("text/plain"), binding.etPassword.getText().toString());
        RequestBody user_mobile = RequestBody.create(MediaType.parse("text/plain"), binding.etMoile.getText().toString());
        RequestBody user_countryId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(countryId));
        RequestBody user_cityId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(cityId));

        viewModel.registerUser(pic, user_name, user_email, user_password,
                user_mobile, user_countryId , user_cityId).observe(requireActivity(), new Observer<RegisterResponse>()
        {
            @Override
            public void onChanged(RegisterResponse registerResponse)
            {
                if (registerResponse.getStatus() == 200)
                {
                    sendCode();
                }
                else if (registerResponse.getMessage().equals("The email has already been taken."))
                {
                    Toasty.info(getActivity(), "this email is already exist").show();
                    Toast.makeText(getActivity(), getString(R.string.email_is_take), Toast.LENGTH_SHORT).show();
                    sendCode();
                }
                else
                {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "" + registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendCode()
    {
        binding.progressBar.setVisibility(View.VISIBLE);

        viewModel.sendCode(binding.etEmail.getText().toString()).observe(requireActivity(), new Observer<SendCodeResponse>()
        {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse)
            {
                binding.progressBar.setVisibility(View.GONE);

                if (sendCodeResponse.getStatus() == 200)
                {
                    Toasty.success(getActivity(), " success").show();

                    navController.navigate(R.id.register_to_activation);
                }
                else
                {
                    Toast.makeText(getActivity(), "" + sendCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

/*
    private void buildCountriesSpinner()
    {
        CountriesAdapter countriesAdapter = new CountriesAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, countriesList);
        binding.spinCountry.setAdapter(countriesAdapter);
        binding.spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                countryId = countriesList.get(pos).getId();
                getCities(countriesList.get(pos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void getCities(int countryId)
    {
        viewModel.getAllCities(countryId).observe(requireActivity(), new Observer<List<CityData>>()
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
        CitiesAdapter citiesAdapter = new CitiesAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1, citiesList);
        binding.spinCity.setAdapter(citiesAdapter);
        binding.spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                cityId = citiesList.get(pos).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }
*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri mImageUri = data.getData();
            imageFile = new File(mImageUri.getLastPathSegment());
            try {
                bitmapPhoto = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.ivImage.setImageBitmap(bitmapPhoto);
            InputStream is = null;
            try
            {
                is = getActivity().getContentResolver().openInputStream(data.getData());
                uploadImage(getBytes(is));
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    private void uploadImage(byte[] imageBytes)
    {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

        pic = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
    }
}