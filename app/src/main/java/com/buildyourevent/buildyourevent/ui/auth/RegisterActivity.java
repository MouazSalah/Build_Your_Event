package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterRequest;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.ui.Adapter.CitiesAdapter;
import com.buildyourevent.buildyourevent.ui.Adapter.CountriesAdapter;
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
    @BindView(R.id.conditions_layout)
    ScrollView conditionLayout;
    @BindView(R.id.register_layout)
    LinearLayout registerLayout;
    @BindView(R.id.condition_button)
    Button conditonBtn;

    int countryId, cityId;

    static Bitmap bitmapPhoto;
    private int PICK_IMAGE_REQUEST = 1;
    MultipartBody.Part pic = null;
    File imageFile;

    List<CountryData> countriesList = new ArrayList<>();
    List<CityData> citiesList = new ArrayList<>();

    UserViewModel viewModel;
    SharedPrefMethods prefMethods;

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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.getAllCountries().observe(this, new Observer<List<CountryData>>() {
            @Override
            public void onChanged(List<CountryData> countryData) {
                countriesList = countryData;
                buildCountriesSpinner();
            }
        });
    }

    @OnClick(R.id.register_signup_button)
    void onSignupButton(View v) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (etUserName.getText().toString().isEmpty())
        {
            etUserName.setError(getString(R.string.enter_name));
        } else if (etEmail.getText().toString().isEmpty())
        {
            etEmail.setError(getString(R.string.enter_email));
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(getString(R.string.enter_password));
        } else if (!etEmail.getText().toString().matches(emailPattern)) {
            etEmail.setError(getString(R.string.invalid_email));
        } else if (etPassword.getText().toString().trim().length() < 6) {
            etPassword.setError(getString(R.string.short_password));
        } else if (etMobile.getText().toString().isEmpty()) {
            etMobile.setError(getString(R.string.enter_mobile));
        } else if (!conditionCheckBox.isChecked()) {
            Toast.makeText(this, getString(R.string.condition_toast), Toast.LENGTH_SHORT).show();
        } else if (countrySpinner == null || countrySpinner.getSelectedItem() == null) {
            Toast.makeText(this, getString(R.string.choose_country), Toast.LENGTH_SHORT).show();
        } else if (citiesList == null || citySpinner.getSelectedItem() == null) {
            Toast.makeText(this, getString(R.string.choose_city), Toast.LENGTH_SHORT).show();
        } else {
            registerProgressBar.setVisibility(View.VISIBLE);
            registerTask();
        }
    }

    public void registerTask()
    {
        RegisterRequest userRequest = new RegisterRequest(etUserName.getText().toString(),etEmail.getText().toString() ,
                etPassword.getText().toString(), etMobile.getText().toString(), countryId, cityId, bitmapPhoto);

        RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), etUserName.getText().toString());
        RequestBody user_email = RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString());
        RequestBody user_password = RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString());
        RequestBody user_mobile = RequestBody.create(MediaType.parse("text/plain"), etMobile.getText().toString());
        RequestBody user_countryId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(countryId));
        RequestBody user_cityId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(cityId));

        viewModel.registerUser(pic, user_name, user_email, user_password,
                user_mobile, user_countryId , user_cityId).observe(this, new Observer<RegisterResponse>()
        {
            @Override
            public void onChanged(RegisterResponse registerResponse)
            {
                if (registerResponse.getStatus() == 200)
                {
                    registerProgressBar.setVisibility(View.GONE);
                    sendCode();
                }
                else if (registerResponse.getMessage().equals("The email has already been taken."))
                {
                    Toast.makeText(RegisterActivity.this, getString(R.string.email_is_take), Toast.LENGTH_SHORT).show();
                    registerProgressBar.setVisibility(View.GONE);
                    sendCode();
                }
                else
                {
                    registerProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "" + registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendCode()
    {
        registerProgressBar.setVisibility(View.VISIBLE);

        viewModel.sendCode(etEmail.getText().toString()).observe(this, new Observer<SendCodeResponse>()
        {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse)
            {
                if (sendCodeResponse.getStatus() == 200)
                {
                    Intent intent = new Intent(getApplicationContext(), ConditionsActivity.class);
                    intent.putExtra(Codes.RECOVERY_EMAIL, etEmail.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "" + sendCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

                registerProgressBar.setVisibility(View.GONE);
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
        viewModel.getAllCities(countryId).observe(this, new Observer<List<CityData>>()
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
        CitiesAdapter citiesAdapter = new CitiesAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                                        android.R.id.text1, citiesList);
        citySpinner.setAdapter(citiesAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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

    @OnClick(R.id.register_login_button)
    void startLoginIntent() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.conditions_checkbox)
    void showConditions(View v)
    {
        // conditionLayout.setVisibility(View.VISIBLE);
       // registerLayout.setAlpha((float) .1);
    }

    @OnClick(R.id.condition_button)
    void agreeConditions(View v)
    {
       // conditionLayout.setVisibility(View.GONE);
       // conditionCheckBox.setChecked(true);
       // registerLayout.setAlpha((float) 1);
    }


    @OnClick(R.id.register_userimage)
    void selectUserImage(View v)
    {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
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
