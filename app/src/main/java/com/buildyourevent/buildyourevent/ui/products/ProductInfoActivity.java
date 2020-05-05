package com.buildyourevent.buildyourevent.ui.products;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartResponse;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartsRequest;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsData;
import com.buildyourevent.buildyourevent.model.data.productrate.ProductRateRequest;
import com.buildyourevent.buildyourevent.model.data.productrate.ProductRateResponse;
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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProductInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.subcategory_imageview)
    CircleImageView subCategoryImage;
    @BindView(R.id.subcategoryname_textview)
    TextView subCategoryName;
    @BindView(R.id.product_imageview)
    ImageView productImg;
    @BindView(R.id.productdetails_nametext)
    TextView productName;
    @BindView(R.id.details_price)
    TextView productPrice;

    @BindView(R.id.details_quantity) TextView availableQuantity;
    @BindView(R.id.details_qtytext) TextView tvQuantity;

    @BindView(R.id.payment_layout)
    LinearLayout paymentLayout;

    @BindView(R.id.details_cityspinner)
    Spinner spinnerCities;
    List<CityData> citiesList = new ArrayList<>();

    @BindView(R.id.details_startday)
    TextView startDayTextView;
    @BindView(R.id.details_startmonth)
    TextView startMonthTextView;
    @BindView(R.id.details_startyear)
    TextView startYearTextView;
    @BindView(R.id.details_endday)
    TextView endDayTextView;
    @BindView(R.id.details_endmonth)
    TextView endMonthTextView;
    @BindView(R.id.ratingbar)
    RatingBar ratingBar;
    @BindView(R.id.details_endyear)
    TextView endYearTextView;

    @BindView(R.id.details_daytext) TextView tvDays;

    @BindView(R.id.rate_layout)
    LinearLayout rateLayout;
    @BindView(R.id.btn_ratebar)
    Button rateBtn;
    @BindView(R.id.btn_showratinglayout)
    Button btnShowRatingLayout;

    @BindView(R.id.productdata_layout)
    LinearLayout dataLayout;
    @BindView(R.id.details_progressbar)
    ProgressBar detailsProgressBar;

    ProductDetailsData productDetailsData;

    int qtyCounter = 0;
    int daysCounter = 0;

    private int startDay, startMonth, startYear, endDay, endMonth, endYear;

    UserViewModel viewModel;
    UserData userData;
    SharedPrefMethods prefMethods;
    SubCategoryData subCategoryData;

    double lat, log;
    String address;
    String cityName;

    FusedLocationProviderClient mFusedLocationClient;

    private GoogleMap mMap;

    Calendar calendar;

    boolean isProductExist = false;

    @BindView(R.id.subcategorydata_layout)
    RelativeLayout subCategoryDataLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, this.getBaseContext().getResources().getDisplayMetrics());

        ButterKnife.bind(this);

/*
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = beginTransaction();
        fragmentTransaction.add(R.id.map_fragment, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);
*/

        getLocation();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d(Codes.APP_TAGS, "GPS is Enabled in your devide");
        } else {
            showGPSDisabledAlertToUser();
        }

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Log.d(Codes.APP_TAGS, "product details Id: " + prefMethods.getProductId());

        if (prefMethods.getSubCategoryData() != null) {
            subCategoryData = prefMethods.getSubCategoryData();
        }

        if (prefMethods.getUserData() != null) {
            userData = prefMethods.getUserData();
        }
        viewModel.getProductDetails(prefMethods.getProductId()).observe(this, new Observer<ProductDetailsData>() {
            @Override
            public void onChanged(ProductDetailsData data) {
                productDetailsData = data;
                setupDataToViews(data);
                Log.d(Codes.APP_TAGS, "product details data: " + productDetailsData.getName());
            }
        });

        tvQuantity.setText("" + 0);
        getCities(userData.getCountryId());
         calendar = Calendar.getInstance();
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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

    private void setupDataToViews(ProductDetailsData productDetailsData)
    {
        if (subCategoryData != null)
        {
            Glide.with(this).load(subCategoryData.getSubcategoryImage()).into(subCategoryImage);
            subCategoryName.setText(subCategoryData.getSubcategoryName());
        }

        Glide.with(this).load(productDetailsData.getImage()).into(productImg);
        productName.setText(productDetailsData.getName());
        productPrice.setText("" + productDetailsData.getPrice());
        availableQuantity.setText("" + productDetailsData.getNewAvailableQty());
        tvDays.setText("0");
        tvQuantity.setText("0");

        Log.d(Codes.APP_TAGS, "product data set to views");
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
        spinnerCities.setAdapter(citiesAdapter);
        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    @OnClick(R.id.selectlocationonmap_button)
    void selectLocation(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

        List<Double> list = new ArrayList<>();
        list.add(lat);
        list.add(log);
        prefMethods.saveUserCandidates(list);
    }

    @OnClick(R.id.product_back_imageview)
    void goBack(View v) {
        Fragment currentFragment = new ProductsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, currentFragment);
        ft.commit();
    }

    @OnClick(R.id.buyproduct_button)
    void buyProduct(View view) {
        paymentLayout.setVisibility(View.VISIBLE);
        dataLayout.setAlpha((float) .1);
    }

    @OnClick(R.id.btn_showratinglayout)
    void showRatingLayout(View v) {
        rateLayout.setVisibility(View.VISIBLE);
        dataLayout.setAlpha((float) .1);
    }

    @OnClick(R.id.btn_ratebar)
    void rateProduct(View v) {
        detailsProgressBar.setVisibility(View.VISIBLE);

        ProductRateRequest request = new ProductRateRequest(userData.getId(), userData.getToken(),
                productDetailsData.getProductId(), ratingBar.getNumStars());
        viewModel.rateProduct(request).observe(this, new Observer<ProductRateResponse>() {
            @Override
            public void onChanged(ProductRateResponse productRateResponse) {
                if (productRateResponse.getStatus() == 200) {
                    Toast.makeText(getApplicationContext(), "Thanks", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                }

                detailsProgressBar.setVisibility(View.GONE);
                rateLayout.setVisibility(View.GONE);
                dataLayout.setAlpha((float) 1);
            }
        });
    }

    @OnClick(R.id.continuepayment_button)
    void OpenPaymentActivity(View view) {
        if (userData == null) {
            paymentLayout.setVisibility(View.GONE);
            dataLayout.setAlpha((float) 1);
            Toast.makeText(getApplicationContext(), "you must to login first", Toast.LENGTH_SHORT).show();
            return;
        }

        isProductExistInCarts();
    }

    private void addPdocutTOCarts() {
        try {
            address = getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddToCartsRequest addToCartsRequest = new AddToCartsRequest(lat, log, userData.getId(), prefMethods.getProductId(), qtyCounter, daysCounter,
                productDetailsData.getAvailableDate(), productDetailsData.getAvailableDate(), userData.getToken(), address);

        viewModel.addToCarts(addToCartsRequest).observe(this, new Observer<AddToCartResponse>()
        {
            @Override
            public void onChanged(AddToCartResponse addToCartResponse)
            {
                Log.d(Codes.APP_TAGS, "carts activity // " + addToCartResponse.toString());
                if (addToCartResponse.getStatus() == 200)
                {
                    Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
                    startActivity(intent);
                    detailsProgressBar.setVisibility(View.GONE);
                }
                else
                {
                    dataLayout.setAlpha((float) 1);
                    detailsProgressBar.setVisibility(View.GONE);
                    paymentLayout.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Try Again...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

    public void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        String provider = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }

        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                             @Override
                             public void onComplete(@NonNull Task<Location> task) {
                                 Location location = task.getResult();
                                 if (location == null) {
                                     LocationRequest mLocationRequest = new LocationRequest();
                                     mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                     mLocationRequest.setInterval(0);
                                     mLocationRequest.setFastestInterval(0);
                                     mLocationRequest.setNumUpdates(1);

                                     mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
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


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat = mLastLocation.getLatitude();
            log = mLastLocation.getLongitude();
        }
    };

    public String getAddress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        addresses = geocoder.getFromLocation(lat, log, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        return state + " , " + city + " , " + country;
    }

    @Optional
    @OnClick({R.id.details_startday, R.id.details_startmonth, R.id.details_startyear})
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
    @OnClick({R.id.details_endday, R.id.details_endmonth, R.id.details_endyear})
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
                //  checkProductValidationDate();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endTime.show();
    }

    @OnClick(R.id.details_incrementqty)
    void incrementQty(View v) {
        qtyCounter++;
        tvQuantity.setText("" + qtyCounter);
    }

    @OnClick(R.id.details_decrementqty)
    void decrementQty(View v) {
        if (qtyCounter != 0) {
            qtyCounter--;
            tvQuantity.setText("" + qtyCounter);
        }
    }

    @OnClick(R.id.details_incrementdays)
    void incrementDays(View v) {
        daysCounter++;
        tvDays.setText("" + daysCounter);
    }

    @OnClick(R.id.details_decrementdays)
    void decrementDays(View v) {
        if (daysCounter != 0) {
            daysCounter--;
            tvDays.setText("" + daysCounter);
        }
    }

    public void isProductExistInCarts()
    {
        detailsProgressBar.setVisibility(View.VISIBLE);
        viewModel.getAllCarts(userData.getId(), userData.getToken()).observe(this, new Observer<CartResponse>()
        {
            @Override
            public void onChanged(CartResponse cartResponse)
            {
                if (cartResponse.getStatus() == 200)
                {
                    for (int i = 0; i < cartResponse.getData().size(); i++)
                    {
                        if (cartResponse.getData().get(i).getProductId() == prefMethods.getProductId())
                        {
                            paymentLayout.setVisibility(View.GONE);
                            dataLayout.setAlpha((float) 1);
                            detailsProgressBar.setVisibility(View.GONE);
                            Toast.makeText(ProductInfoActivity.this, "This product already exist in carts", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            paymentLayout.setVisibility(View.GONE);
                            dataLayout.setAlpha((float) 1);
                            detailsProgressBar.setVisibility(View.GONE);
                            addPdocutTOCarts();

                        }
                    }
                }
                else
                {
                    addPdocutTOCarts();
                }
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public boolean checkProductValidationDate() throws ParseException
    {
        boolean result = false;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse(startDay + "-" + startMonth + "-" + startYear);
        Date d2 = sdformat.parse(endDay + "-" + endMonth + "-" + endYear);

        if(d1.compareTo(d2) > 0)
        {
            Toast.makeText(this, "Date 1 occurs after Date 2", Toast.LENGTH_SHORT).show();
            result = false;
        }
        else if(d1.compareTo(d2) < 0)
        {
            System.out.println("Date 1 occurs before Date 2");
            result = true;
        }
        else if(d1.compareTo(d2) == 0)
        {
            System.out.println("Both dates are equal");
            result = false;
        }

        return result;
    }
}
