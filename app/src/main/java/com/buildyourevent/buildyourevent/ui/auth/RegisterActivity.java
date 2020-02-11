package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterRequest;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    @BindView(R.id.country_spinner) Spinner countrySpinner;
    @BindView(R.id.city_spinner) Spinner citySpinner;

    @BindView(R.id.register_userimage) CircleImageView userImage;
    @BindView(R.id.register_progressbar)
    ProgressBar registerProgressBar;
    @BindView(R.id.conditions_checkbox)
    CheckBox conditionCheckBox;

    int countryId, cityId;

    static Bitmap bitmapPhoto;
    private int PICK_IMAGE_REQUEST = 1;

    List<CountryData> countriesList = new ArrayList<>();
    List<CityData> citiesList = new ArrayList<>();

    File imageFile;
    UserViewModel viewModel;

    MultipartBody.Part pic = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPrefMethods prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    @OnClick(R.id.register_signup_button)
    void onSignupButton(View v)
    {
        registerProgressBar.setVisibility(View.VISIBLE);
        if (checkMandatoryFields())
        {
            RegisterRequest userRequest = new RegisterRequest(etUserName.getText().toString(),etEmail.getText().toString() ,
                    etPassword.getText().toString(), etMobile.getText().toString(), countryId, cityId, bitmapPhoto);

            RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), etUserName.getText().toString());
            RequestBody user_email = RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString());
            RequestBody user_password = RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString());
            RequestBody user_mobile = RequestBody.create(MediaType.parse("text/plain"), etMobile.getText().toString());
            RequestBody user_countryId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(countryId));
            RequestBody user_cityId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(cityId));

            Log.d(Codes.APP_TAGS, "request format: // " + userRequest);

            viewModel.registerUser(pic, user_name, user_email, user_password, user_mobile, user_countryId, user_cityId).observe(this, new Observer<RegisterResponse>()
            {
                @Override
                public void onChanged(RegisterResponse registerResponse)
                {
                    if (registerResponse.getStatus() == 200)
                    {
                        Log.d(Codes.APP_TAGS, "register activity");
                        Log.d(Codes.APP_TAGS, "register activity" + registerResponse.getMessage());
                        sendCode("register");
                        registerProgressBar.setVisibility(View.GONE);
                    }
                    else if (registerResponse.getMessage().equals("The email has already been taken."))
                    {
                        Toast.makeText(RegisterActivity.this, getString(R.string.email_is_take), Toast.LENGTH_SHORT).show();
                        sendCode("reset_password");
                        registerProgressBar.setVisibility(View.GONE);
                    }
                    else
                    {
                        registerProgressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "" + registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public boolean checkMandatoryFields()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (etUserName.getText().toString().isEmpty()) {
            etUserName.setError(getString(R.string.enter_name));
            return false;
        } else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError(getString(R.string.enter_email));
            return false;
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (!etEmail.getText().toString().matches(emailPattern)) {
            etEmail.setError(getString(R.string.invalid_email));
            return false;
        } else if (etPassword.getText().toString().trim().length() < 6) {
            etPassword.setError(getString(R.string.short_password));
            return false;
        }
        else if (etMobile.getText().toString().isEmpty())
        {
            etMobile.setError(getString(R.string.enter_mobile));
            return false;
        }
        else if (!conditionCheckBox.isChecked())
        {
            Toast.makeText(this, getString(R.string.condition_toast), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (countrySpinner == null || countrySpinner.getSelectedItem() == null) {
            Toast.makeText(this, getString(R.string.choose_country), Toast.LENGTH_SHORT).show();
            return false;
        } else if (citiesList == null || citySpinner.getSelectedItem() == null) {
            Toast.makeText(this, getString(R.string.choose_city), Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    private void sendCode(String intentValue)
    {
        viewModel.sendCode(etEmail.getText().toString()).observe(this, new Observer<SendCodeResponse>() {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse) {
                if (sendCodeResponse.getStatus() == 200)
                {
                    Intent intent = new Intent(getApplicationContext(), VerifyCodeActivity.class);
                    intent.putExtra(Codes.VERIFY_CODE_INTENT, intentValue);
                    intent.putExtra(Codes.RECOVERY_EMAIL, etEmail.getText().toString());
                    startActivity(intent);
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
            Uri mImageUri = data.getData();
            imageFile = new File(mImageUri.getLastPathSegment());
            try {
                bitmapPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            userImage.setImageBitmap(bitmapPhoto);
            InputStream is = null;
            try
            {
                is = getContentResolver().openInputStream(data.getData());
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
