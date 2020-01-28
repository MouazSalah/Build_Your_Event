package com.buildyourevent.buildyourevent.ui.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartResponse;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartsRequest;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.ui.MapsActivity;
import com.buildyourevent.buildyourevent.ui.auth.CitiesAdapter;
import com.buildyourevent.buildyourevent.ui.auth.CountriesAdapter;
import com.buildyourevent.buildyourevent.ui.cardactivity.CartsActivity;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.AuthViewModel;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hzn.lib.EasyTransition;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProductDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.subcategory_imageview)
    CircleImageView subCategoryImage;
    @BindView(R.id.subcategoryname_textview)
    TextView subCategoryName;
    @BindView(R.id.product_imageview)
    ImageView productImg;
    @BindView(R.id.producttitle_textview)
    TextView productName;
    @BindView(R.id.producprice_textview)
    TextView productPrice;
    @BindView(R.id.availableamount_textview)
    TextView productAmount;
    @BindView(R.id.qty_textview)
    TextView qtyTextView;
    @BindView(R.id.days_textviw)
    TextView daysTextView;
    @BindView(R.id.productcountry_spinner)
    Spinner countrySpinner;
    @BindView(R.id.productcity_spinner)
    Spinner citySpinner;

    @BindView(R.id.payment_layout)
    LinearLayout paymentLayout;

    @BindView(R.id.startday_textview)
    TextView startDayTextView;
    @BindView(R.id.startmonth_textview)
    TextView startMonthTextView;
    @BindView(R.id.startyear_textview)
    TextView startYearTextView;
    @BindView(R.id.endday_textview)
    TextView endDayTextView;
    @BindView(R.id.endmonth_textview)
    TextView endMonthTextView;
    @BindView(R.id.endyear_textview)
    TextView endYearTextView;

    @BindView(R.id.productdata_layout)
    LinearLayout dataLayout;

    int qtyCounter = 0;
    int daysCounter = 0;

    private Calendar calendar;
    private int startDay, startMonth, startYear, endDay, endMonth, endYear;
    private DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    UserViewModel viewModel;
    AuthViewModel authViewModel;
    int productId;
    UserData userData;
    SharedPrefMethods prefMethods;
    SubCategoryData subCategoryData;

    double lat, log;
    String address;


    String countryName, cityName;
    List<CountryData> countriesList = new ArrayList<>();
    List<CityData> citiesList = new ArrayList<>();

    private GoogleMap mMap;

    LocationManager locationManager;

    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        // onCreate
        EasyTransition.enter(ProductDetailsActivity.this);

        productId = getIntent().getIntExtra(Codes.PRODUCT_ID, 0);
        Log.d(Codes.APP_TAGS, "product details Id: " + productId);

        prefMethods = new SharedPrefMethods(this);
        subCategoryData = prefMethods.getSubCategoryData();

        userData = prefMethods.getUserData();
        viewModel.getProductDetails(productId).observe(this, new Observer<ProductDetailsData>() {
            @Override
            public void onChanged(ProductDetailsData productDetailsData) {
                setupDataToViews(productDetailsData);
                Log.d(Codes.APP_TAGS, "product details data: " + productDetailsData.getName());
            }
        });

        qtyTextView.setText("" + 0);
        daysTextView.setText("" + 0);

        calendar = Calendar.getInstance();


        authViewModel.getAllCountries().observe(this, new Observer<List<CountryData>>() {
            @Override
            public void onChanged(List<CountryData> countryData) {
                countriesList = countryData;
                buildCountriesSpinner();
            }
        });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(Codes.APP_TAGS, "location" + location.toString());
                lat = location.getLatitude();
                log = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };

        // If device is running SDK < 23

        if (Build.VERSION.SDK_INT < 23) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    return;
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    private void buildCountriesSpinner() {
        CountriesAdapter countriesAdapter = new CountriesAdapter(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, countriesList);
        countrySpinner.setAdapter(countriesAdapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                countryName = countriesList.get(pos).getCountryName();
                getCities(countriesList.get(pos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCities(int countryId) {
        Log.d(Codes.APP_TAGS, "get cities method");
        authViewModel.getAllCities(countryId).observe(this, new Observer<List<CityData>>() {
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
                cityName = citiesList.get(pos).getCityName();
                Log.e(TAG, "onItemSelected: " + citiesList.get(pos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setupDataToViews(ProductDetailsData productDetailsData) {
        Glide.with(this).load(subCategoryData.getSubcategoryImage()).into(subCategoryImage);
        subCategoryName.setText("" + subCategoryData.getSubcategoryName());
        Glide.with(this).load(productDetailsData.getImage()).into(productImg);
        productName.setText(productDetailsData.getName());
        productPrice.setText("" + productDetailsData.getPrice());
        productAmount.setText("" + productDetailsData.getAvailableQuantity());
        qtyTextView.setText("" + productDetailsData.getAvailableQuantity());

        Log.d(Codes.APP_TAGS, "product data set to views");
    }


    private void addPdocutTOCarts() {
        String startDate = startDay + "/" + startMonth + "/" + startYear;
        String endDate = endDay + "/" + endMonth + "/" + endYear;
        AddToCartsRequest addToCartsRequest = new AddToCartsRequest(1, 2, userData.getId(), productId, qtyCounter, daysCounter,
                startDate, endDate, userData.getToken(), userData.getCityName());

        viewModel.addToCarts(addToCartsRequest).observe(this, new Observer<AddToCartResponse>() {
            @Override
            public void onChanged(AddToCartResponse addToCartResponse) {
                Log.d(Codes.APP_TAGS, "carts activity // " + addToCartResponse.getMessage());
                if (addToCartResponse.getStatus() == 200) {
                    Toast.makeText(ProductDetailsActivity.this, "Added To carts", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ProductDetailsActivity.this, "Try Later...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @OnClick(R.id.selectlocationonmap_button)
    void selectLocation(View v) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.product_back_imageview)
    void goBack(View v) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.buyproduct_button)
    void buyProduct(View view) {
        paymentLayout.setVisibility(View.VISIBLE);
        dataLayout.setAlpha((float) .1);
    }


    @OnClick(R.id.continuepayment_button)
    void OpenPaymentActivity(View view) {
        if (userData == null) {
            paymentLayout.setVisibility(View.GONE);
            dataLayout.setAlpha((float) 1);
            Toast.makeText(this, "you must to login first", Toast.LENGTH_SHORT).show();
            return;
        }
        addPdocutTOCarts();
    }


    @Optional
    @OnClick({R.id.startday_textview, R.id.startmonth_textview, R.id.startyear_textview})
    void selectStartDate(View view) {
        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //calendar.set(1, dayOfMonth);

                startDay = dayOfMonth;
                startYear = year;
                String myFormat = "MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String dateFormat = sdf.format(calendar.getTime());

                if (dateFormat.length() > 0) {
                    String[] mthYr = dateFormat.toString().split("/");
                    // setDay(Integer.valueOf(mthYr[0]));
                    startMonth = (Integer.valueOf(mthYr[0]));
                }
                startDayTextView.setText("" + startDay);
                startMonthTextView.setText("" + startMonth);
                startYearTextView.setText("" + startYear);

                Log.d(Codes.APP_TAGS, "" + startDay);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.show();

    }

    @Optional
    @OnClick({R.id.endday_textview, R.id.endmonth_textview, R.id.endyear_textview})
    void selectEndDate(View view) {
        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog endTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //view.findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //calendar.set(1, dayOfMonth);

                endDay = dayOfMonth;
                endYear = year;
                String myFormat = "MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String dateFormat = sdf.format(calendar.getTime());

                if (dateFormat.length() > 0) {
                    String[] mthYr = dateFormat.toString().split("/");
                    // setDay(Integer.valueOf(mthYr[0]));
                    endMonth = (Integer.valueOf(mthYr[0]));
                }
                endDayTextView.setText("" + endDay);
                endMonthTextView.setText("" + endMonth);
                endYearTextView.setText("" + endYear);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endTime.show();
    }

    @OnClick(R.id.increment_qty)
    void incrementQty(View v) {
        qtyCounter++;
        qtyTextView.setText("" + qtyCounter);
    }

    @OnClick(R.id.decrement_qty)
    void decrementQty(View v) {
        if (qtyCounter != 0) {
            qtyCounter--;
            qtyTextView.setText("" + qtyCounter);
        }
    }

    @OnClick(R.id.increment_days)
    void incrementDays(View v) {
        daysCounter++;
        daysTextView.setText("" + daysCounter);
    }

    @OnClick(R.id.decrement_days)
    void decrementDays(View v) {
        if (daysCounter != 0) {
            daysCounter--;
            daysTextView.setText("" + daysCounter);
        }
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public DatePickerDialog.OnDateSetListener getDate() {
        return startDatePicker;
    }

    private void splitData() {
        String stringPath = "7/1/2020";
        String[] tokens = stringPath.split("/");
        Log.e(Codes.APP_TAGS, "date: " + tokens[0] + tokens[1] + tokens[2]);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (Build.VERSION.SDK_INT < 23) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15.0f));
            }
        }
    }

    public String getAddress() throws IOException
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(lat, log, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        return state + " , " + city + " , " + country;
    }
}
