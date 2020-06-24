package com.buildyourevent.buildyourevent.user.home;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.HomeFragmentProductDetailsBinding;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsData;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.ui.auth.CitiesAdapter;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProductDetailsFragment extends Fragment
{
    HomeFragmentProductDetailsBinding binding;
    ProductDetailsViewModel viewModel;
    NavController navController;

    ProductDetailsData productDetailsData;

    int qtyCounter = 0;
    int daysCounter = 0;

    private int startDay, startMonth, startYear, endDay, endMonth, endYear;

    UserData userData;
    int countryId;
    SharedPrefMethods prefMethods;
    SubCategoryData subCategoryData;

    double lat, log;
    String address;
    String cityName;

    FusedLocationProviderClient mFusedLocationClient;
    LocationManager locationManager;

    private GoogleMap mMap;

    Calendar calendar;

    boolean isProductExist = false;

    ImageView subImage = binding.subcategoryImageview;
    TextView subName = binding.subcategorynameTextview;
    TextView productName = binding.productdetailsNametext;
    TextView productQty = binding.detailsQuantity;
    TextView productPrice = binding.detailsPrice;

    TextView quantityText = binding.detailsQtytext;
    TextView daysText = binding.detailsDaytext;
    TextView increaseQty = binding.detailsIncrementqty;
    TextView decreaseQty = binding.detailsDecrementqty;
    TextView increaseDay = binding.detailsIncrementdays;
    TextView decreaseDay = binding.detailsDecrementdays;

    TextView startDayText = binding.detailsStartday;
    TextView startMonthText = binding.detailsStartmonth;
    TextView startYearText = binding.detailsStartyear;
    TextView endDayText = binding.detailsEndday;
    TextView endMonthText = binding.detailsEndmonth;
    TextView endYearText = binding.detailsEndyear;

    Spinner citySpinner = binding.detailsCityspinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        binding = HomeFragmentProductDetailsBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ProductDetailsViewModel.class);

        navController = Navigation.findNavController(requireActivity(), R.id.auth_navigation);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
          //  getLocation();
            Log.d(Codes.APP_TAGS, "GPS is Enabled in your devide");
        } else {
            showGPSDisabledAlertToUser();
        }

        Log.d(Codes.APP_TAGS, "product details Id: " + prefMethods.getProductId());

        if (prefMethods.getSubCategoryData() != null) {
            subCategoryData = prefMethods.getSubCategoryData();
        }

        if (prefMethods.getUserData() != null)
        {
            userData = prefMethods.getUserData();
          //  getCities(userData.getCountryId());
        }
        else
        {
            countryId = prefMethods.getCountryId();
            //getCities(countryId);
        }

        viewModel.getProductData(prefMethods.getProductId()).observe(getActivity(), new Observer<ProductDetailsResponse>()
        {
            @Override
            public void onChanged(ProductDetailsResponse response)
            {
                productDetailsData = response.getData();
              //  setupDataToViews();
            }
        });

        // tvQuantity.setText("" + 0);
        calendar = Calendar.getInstance();

        return binding.getRoot();
    }

/*
    @OnClick(R.id.selectlocationonmap_button)
    void selectLocation(View v)
    {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            List<Double> list = new ArrayList<>();
            list.add(lat);
            list.add(log);

            Log.d(Codes.APP_TAGS, "latitude info = " + lat);
            Log.d(Codes.APP_TAGS, "longitude info = " + log);
            prefMethods.saveUserCandidates(list);

            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);

            // getLocation();
        } else {
            showGPSDisabledAlertToUser();
        }
    }
*/

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder
                .setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
/*
    private void setupDataToViews()
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
    }*/
/*

    @OnClick(R.id.product_back_imageview)
    void goBack(View v)
    {
        Intent intent = new Intent(getActivity(), SubCategoryActivity.class);
        startActivity(intent);
     */
/*   Fragment currentFragment = new ProductsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, currentFragment);
        ft.commit();*//*

    }
*/

/*
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
                    Toast.makeText(getActivity(), "Thanks", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Try again", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "you must to login first", Toast.LENGTH_SHORT).show();
            return;
        }

        isProductExistInCarts();
    }
*/

/*
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
*/

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {//Can add more as per requirement

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

    public void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
          //  sendBroadcast(poke);
        }

        /*mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
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
        );*/
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

/*
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
*/

/*
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
*/

/*
    public void isProductExistInCarts()
    {
       // detailsProgressBar.setVisibility(View.VISIBLE);
        viewModel.getAllCarts(userData.getId(), userData.getToken()).observe(this, new Observer<CartResponse>()
        {
            @Override
            public void onChanged(CartResponse cartResponse)
            {
                Log.d(Codes.APP_TAGS, "cart response // " + cartResponse.getMessage());
                if (cartResponse.getStatus() == 200 )
                {
                    if (cartResponse.getData().size() != 0)
                    {
                        for (int i = 0; i < cartResponse.getData().size(); i++)
                        {
                            if (cartResponse.getData().get(i).getProductId() == prefMethods.getProductId())
                            {
                                Log.d(Codes.APP_TAGS, "product exist");
                                paymentLayout.setVisibility(View.GONE);
                                dataLayout.setAlpha((float) 1);
                                detailsProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "This product already exist in carts", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), CartsActivity.class));
                            }
                            else
                            {
                                Log.d(Codes.APP_TAGS, "product not exist");
                                paymentLayout.setVisibility(View.GONE);
                                dataLayout.setAlpha((float) 1);
                                detailsProgressBar.setVisibility(View.GONE);
                                addProductToCarts();
                            }
                        }
                    }
                    else
                    {
                        Log.d(Codes.APP_TAGS, "product addedd");
                        paymentLayout.setVisibility(View.GONE);
                        dataLayout.setAlpha((float) 1);
                        detailsProgressBar.setVisibility(View.GONE);
                        addProductToCarts();
                    }
                }
                else
                {
                    Log.d(Codes.APP_TAGS, "product upd add to carts");
                    paymentLayout.setVisibility(View.GONE);
                    dataLayout.setAlpha((float) 1);
                    detailsProgressBar.setVisibility(View.GONE);
                    addProductToCarts();
                }
            }
        });
    }

    private void addProductToCarts()
    {
        address = cityName;

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
                    int count = prefMethods.getCartsCount();
                    prefMethods.saveCartsCount(count + 1);
                    Intent intent = new Intent(getActivity(), CartsActivity.class);
                    startActivity(intent);
                    detailsProgressBar.setVisibility(View.GONE);
                }
                else
                {
                    dataLayout.setAlpha((float) 1);
                    detailsProgressBar.setVisibility(View.GONE);
                    paymentLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "This Product is already exist in carts", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), CartsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
*/

    public boolean checkProductValidationDate() throws ParseException
    {
        boolean result = false;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse(startDay + "-" + startMonth + "-" + startYear);
        Date d2 = sdformat.parse(endDay + "-" + endMonth + "-" + endYear);

        if(d1.compareTo(d2) > 0)
        {
            Toast.makeText(getActivity(), "Date 1 occurs after Date 2", Toast.LENGTH_SHORT).show();
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
