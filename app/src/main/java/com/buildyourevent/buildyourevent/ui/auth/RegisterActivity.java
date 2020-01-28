package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterRequest;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.viewmodel.AuthViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RegisterActivity extends AppCompatActivity
{
    @BindView(R.id.register_username)
    EditText etUserName;
    @BindView(R.id.register_email)
    EditText etEmail;
    @BindView(R.id.register_password)
    EditText etPassword;
    @BindView(R.id.register_mobile) EditText etMobile;

    @BindView(R.id.country_spinner)
    Spinner countrySpinner;
    @BindView(R.id.city_spinner)
    Spinner citySpinner;
    @BindView(R.id.register_userimage) CircleImageView userImage;

    int countryId, cityId;

    static Bitmap bitmapPhoto;
    private int PICK_IMAGE_REQUEST = 1;

    List<CountryData> countriesList = new ArrayList<>();
    List<CityData> citiesList = new ArrayList<>();

    AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        viewModel.getAllCountries().observe(this, new Observer<List<CountryData>>() {
            @Override
            public void onChanged(List<CountryData> countryData) {
                countriesList = countryData;
                buildCountriesSpinner();
            }
        });
    }


    @OnClick(R.id.register_signup_button)
    void onSignupButton(View v)
    {
        Toast.makeText(this, "onclick register", Toast.LENGTH_SHORT).show();
        if (checkMandatoryFields())
        {
            RegisterRequest userRequest = new RegisterRequest(etUserName.getText().toString(),etEmail.getText().toString() ,
                    etPassword.getText().toString(), etMobile.getText().toString(), countryId, cityId, bitmapPhoto);

            Log.d(Codes.APP_TAGS, "request format: // " + userRequest);

            viewModel.registerUser(userRequest).observe(this, new Observer<RegisterResponse>()
            {
                @Override
                public void onChanged(RegisterResponse registerResponse)
                {
                    if (registerResponse.getStatus() == 200)
                    {
                        Log.d(Codes.APP_TAGS, "register activity");
                        Log.d(Codes.APP_TAGS, "register activity" + registerResponse.getMessage());
                        sendCodeForRegister();
                    }
                    else
                    {
                        Log.d(Codes.APP_TAGS, "regisactivity: failed = " +registerResponse.getMessage());
                        Toast.makeText(RegisterActivity.this, "Try Again...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public boolean checkMandatoryFields()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (etUserName.getText().toString().isEmpty()) {
            etUserName.setError("enter name");
            return false;
        } else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("enter email");
            return false;
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("enter password");
            return false;
        } else if (!etEmail.getText().toString().matches(emailPattern)) {
            etEmail.setError("invalid email");
            return false;
        } else if (etPassword.getText().toString().trim().length() < 6) {
            etPassword.setError("short password");
            return false;
        }
        else if (etMobile.getText().toString().isEmpty())
        {
            etMobile.setError("enter mobile");
            return false;
        }
        else if (countrySpinner == null || countrySpinner.getSelectedItem() == null) {
            Toast.makeText(this, "Choose Your Country", Toast.LENGTH_SHORT).show();
            return false;
        } else if (citiesList == null || citySpinner.getSelectedItem() == null) {
            Toast.makeText(this, "Choose Your City", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    private void sendCodeForRegister()
    {
        viewModel.sendCode(etEmail.getText().toString()).observe(this, new Observer<SendCodeResponse>() {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse) {
                if (sendCodeResponse.getStatus() == 200)
                {
                    Intent intent = new Intent(getApplicationContext(), VerifyCodeActivity.class);
                    intent.putExtra(Codes.VERIFY_CODE_INTENT, "register");
                    intent.putExtra(Codes.RECOVERY_EMAIL, etEmail.getText().toString());
                    startActivity(intent);
                    finish();
                }
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
                countryId = countriesList.get(pos).getId();
                Log.e(TAG, "onItemSelected: " + countriesList.get(pos).getId());
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
                cityId = citiesList.get(pos).getId();
                Log.e(TAG, "onItemSelected: " + citiesList.get(pos).getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    @OnClick(R.id.register_login_button)
    void startLoginIntent() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.register_userimage)
    void selectUserImage(View v)
    {
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri uri = data.getData();
            File fil = new File(uri.getLastPathSegment());
            try
            {
                bitmapPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                userImage.setImageBitmap(bitmapPhoto);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void persistImage() throws IOException
    {
        //create a file to write bitmap data
        File f = new File(getCacheDir(), "userImageFile" + ".jpg");
        f.createNewFile();

        //Convert bitmap to byte array
        Bitmap bitmap = bitmapPhoto;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
    }
}
