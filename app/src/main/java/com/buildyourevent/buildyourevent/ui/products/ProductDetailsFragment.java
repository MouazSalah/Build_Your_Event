package com.buildyourevent.buildyourevent.ui.products;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartResponse;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartsRequest;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.ui.MapsActivity;
import com.buildyourevent.buildyourevent.ui.auth.CitiesAdapter;
import com.buildyourevent.buildyourevent.ui.cardactivity.CartsActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class ProductDetailsFragment extends Fragment implements OnMapReadyCallback
{
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
    // @BindView(R.id.productcountry_spinner) Spinner countrySpinner;
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
    @BindView(R.id.details_progressbar)
    ProgressBar detailsProgressBar;

    ProductDetailsData productDetailsData;

    int qtyCounter = 0;
    int daysCounter = 0;

    private Calendar calendar;
    private int startDay, startMonth, startYear, endDay, endMonth, endYear;
    private int mintDay, minMonth, minYear, maxDay, maxMonth, maxYear;
    private DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    UserViewModel viewModel;
    UserData userData;
    SharedPrefMethods prefMethods;
    SubCategoryData subCategoryData;

    double lat, log;
    String address;

    FusedLocationProviderClient mFusedLocationClient;

    String countryName, cityName;
    List<CountryData> countriesList = new ArrayList<>();
    List<CityData> citiesList = new ArrayList<>();

    private GoogleMap mMap;

    boolean isProductExist = false;

    @BindView(R.id.subcategorydata_layout)
    RelativeLayout subCategoryDataLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        prefMethods = new SharedPrefMethods(getActivity());
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());

        View root = inflater.inflate(R.layout.activity_details, container, false);
        ButterKnife.bind(this, root);

      /*  SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync( this::onMapReady);*/

        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_fragment, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);

        getLocation();

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Log.d(Codes.APP_TAGS, "GPS is Enabled in your devide");
        }
        else
        {
            showGPSDisabledAlertToUser();
        }

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        Log.d(Codes.APP_TAGS, "product details Id: " + prefMethods.getProductId());

        if (prefMethods.getSubCategoryData() != null)
        {
            subCategoryData = prefMethods.getSubCategoryData();
        }

        if (prefMethods.getUserData() != null) {
            userData = prefMethods.getUserData();
        }
        viewModel.getProductDetails(prefMethods.getProductId()).observe(this, new Observer<ProductDetailsData>() {
            @Override
            public void onChanged(ProductDetailsData data)
            {
                productDetailsData = data;
                setupDataToViews(data);
                Log.d(Codes.APP_TAGS, "product details data: " + productDetailsData.getName());
            }
        });

        qtyTextView.setText("" + 0);
        daysTextView.setText("" + 0);

        calendar = Calendar.getInstance();
        if (userData != null)
        {
            getCities(userData.getCountryId());
        }

        // checkProductValidationDate();


/*
        viewModel.getAllCountries().observe(this, new Observer<List<CountryData>>() {
            @Override
            public void onChanged(List<CountryData> countryData) {
                countriesList = countryData;
                buildCountriesSpinner();
            }
        });*/

/*
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                lat = userLocation.latitude;
                log = userLocation.longitude;
                Log.d(Codes.APP_TAGS, "lat location // " + userLocation.latitude);
                Log.d(Codes.APP_TAGS, "log location // " + userLocation.longitude);
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

        if (Build.VERSION.SDK_INT < 23) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } *//*else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                Log.d(Codes.APP_TAGS, "lat / " + userLocation.latitude);
                Log.d(Codes.APP_TAGS, "log / " + userLocation.longitude);
                lat = userLocation.latitude;
                log = userLocation.longitude;
            }
        }*/

        return root;
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


   /* private void buildCountriesSpinner() {
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
    }*/

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
        CitiesAdapter citiesAdapter = new CitiesAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,
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


    private void setupDataToViews(ProductDetailsData productDetailsData)
    {
        if (subCategoryData != null)
        {
            //  subCategoryDataLayout.setVisibility(View.GONE);
            Glide.with(this).load(subCategoryData.getSubcategoryImage()).into(subCategoryImage);
            subCategoryName.setText(subCategoryData.getSubcategoryName());
        }

        Glide.with(this).load(productDetailsData.getImage()).into(productImg);
        productName.setText(productDetailsData.getName());
        productPrice.setText("" + productDetailsData.getPrice());
        productAmount.setText("" + productDetailsData.getAvailableQuantity());
        qtyTextView.setText("" + productDetailsData.getAvailableQuantity());

        startDayTextView.setText(String.valueOf(startDay));

        Log.d(Codes.APP_TAGS, "product data set to views");
    }

    @OnClick(R.id.selectlocationonmap_button)
    void selectLocation(View v)
    {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);

        List<Double> list = new ArrayList<>();
        list.add(lat);
        list.add(log);
        prefMethods.saveUserCandidates(list);
    }

    @OnClick(R.id.product_back_imageview)
    void goBack(View v)
    {
        Fragment currentFragment = new ProductsFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, currentFragment);
        ft.commit();

        /*
        Intent intent = new Intent(getActivity(), ProductsActivity.class);
        startActivity(intent);*/
    }


    @OnClick(R.id.buyproduct_button)
    void buyProduct(View view) {
        paymentLayout.setVisibility(View.VISIBLE);
        dataLayout.setAlpha((float) .1);
    }

    @OnClick(R.id.continuepayment_button)
    void OpenPaymentActivity(View view)
    {
        if (userData == null)
        {
            paymentLayout.setVisibility(View.GONE);
            dataLayout.setAlpha((float) 1);
            Toast.makeText(getActivity(), "you must to login first", Toast.LENGTH_SHORT).show();
            return;
        }

        isProductExistInCarts();
    }

    private void addPdocutTOCarts()
    {
        String startDate = startDay + "/" + startMonth + "/" + startYear;
        String endDate = endDay + "/" + endMonth + "/" + endYear;
        try {
            address = getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddToCartsRequest addToCartsRequest = new AddToCartsRequest(lat, log, userData.getId(), prefMethods.getProductId(), qtyCounter, daysCounter,
                startDate, endDate, userData.getToken(),address);

        viewModel.addToCarts(addToCartsRequest).observe(this, new Observer<AddToCartResponse>() {
            @Override
            public void onChanged(AddToCartResponse addToCartResponse) {
                Log.d(Codes.APP_TAGS, "carts activity // " + addToCartResponse.toString());
                if (addToCartResponse.getStatus() == 200) {
                    Intent intent = new Intent(getActivity(), CartsActivity.class);
                    startActivity(intent);
                    detailsProgressBar.setVisibility(View.GONE);
                } else {
                    dataLayout.setAlpha((float) 1);
                    detailsProgressBar.setVisibility(View.GONE);
                    paymentLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Try Again...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Optional
    @OnClick({R.id.startday_textview, R.id.startmonth_textview, R.id.startyear_textview})
    void selectStartDate(View view) {
        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
    void selectEndDate(View view)
    {
        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog endTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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

                if (dateFormat.length() > 0)
                {
                    String[] mthYr = dateFormat.toString().split("/");
                    // setDay(Integer.valueOf(mthYr[0]));
                    endMonth = (Integer.valueOf(mthYr[0]));
                }
                //  checkProductValidationDate();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endTime.show();
    }

    @OnClick(R.id.increment_qty)
    void incrementQty(View v) {
        qtyCounter++;
        qtyTextView.setText("" + qtyCounter);
    }

    @OnClick(R.id.decrement_qty) void decrementQty(View v) {
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

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            checkPermission();
        }

        getLocation();
        // Add a marker in Sydney and move the camera
        LatLng currentLocation = new LatLng(lat, log);

        mMap.addMarker(new MarkerOptions().position(currentLocation).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16), 5000, null);

    }

    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

    public void  getLocation()
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps"))
        { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            getActivity().sendBroadcast(poke);
        }

        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>()
                 {
                     @Override
                     public void onComplete(@NonNull Task<Location> task) {
                         Location location = task.getResult();
                         if (location == null) {
                             LocationRequest mLocationRequest = new LocationRequest();
                             mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                             mLocationRequest.setInterval(0);
                             mLocationRequest.setFastestInterval(0);
                             mLocationRequest.setNumUpdates(1);

                             mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                             mFusedLocationClient.requestLocationUpdates(
                                     mLocationRequest, mLocationCallback,
                                     Looper.myLooper()
                             );
                         } else {
                             lat = location.getLatitude();
                             log = location.getLongitude();
                         }
                     }
                 }
        );
    }


    private LocationCallback mLocationCallback = new LocationCallback()
    {
        @Override
        public void onLocationResult(LocationResult locationResult)
        {
            Location mLastLocation = locationResult.getLastLocation();
            lat = mLastLocation.getLatitude();
            log = mLastLocation.getLongitude();
        }
    };

    public String getAddress() throws IOException
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        addresses = geocoder.getFromLocation(lat, log, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        return state + " , " + city + " , " + country;
    }


    public void isProductExistInCarts()
    {
        detailsProgressBar.setVisibility(View.VISIBLE);
        viewModel.getAllCarts(userData.getId(), userData.getToken()).observe(this, new Observer<CartResponse>() {
            @Override
            public void onChanged(CartResponse cartResponse)
            {
                if (cartResponse.getStatus() == 200)
                {
                    for (int i = 0; i < cartResponse.getData().size(); i ++)
                    {
                        if (cartResponse.getData().get(i).getProductId() == prefMethods.getProductId())
                        {
                            paymentLayout.setVisibility(View.GONE);
                            dataLayout.setAlpha((float) 1);
                            detailsProgressBar.setVisibility(View.GONE);
                        }
                        else
                        {
                            paymentLayout.setVisibility(View.GONE);
                            dataLayout.setAlpha((float) 1);

                            addPdocutTOCarts();
                        }
                    }
                }
            }
        });

    }

 /*   public void checkProductValidationDate()
    {
        String startDat = "27/1/2020";
        String[] tokens = startDat.split("/");

        endDayTextView.setText("" + endDay);
        endMonthTextView.setText("" + endMonth);
        endYearTextView.setText("" + endYear);

        if (startDay < mintDay && startMonth < minMonth && startYear < minYear)
        {
            startDayTextView.setText("" + mintDay);
            startMonthTextView.setText("" + minMonth);
            startYearTextView.setText("" + minYear);
        }
        else
        {
            endDayTextView.setText("" + maxDay);
            endMonthTextView.setText("" + maxMonth);
            endYearTextView.setText("" + maxYear);
        }
        if (startDay > mintDay && startMonth > minMonth && startYear > minYear)
        {
            endDayTextView.setText("" + maxDay);
            endMonthTextView.setText("" + maxMonth);
            endYearTextView.setText("" + maxYear);
        }
        else
        {

        }
    }*/



}
