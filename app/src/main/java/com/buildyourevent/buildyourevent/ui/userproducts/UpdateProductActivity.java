package com.buildyourevent.buildyourevent.ui.userproducts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.category.CategoryData;
import com.buildyourevent.buildyourevent.model.data.category.CategoryResponse;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsData;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;
import com.buildyourevent.buildyourevent.model.data.updateproduct.UpdateProductResponse;
import com.buildyourevent.buildyourevent.model.data.userproduct.response.UserOwnProductData;
import com.buildyourevent.buildyourevent.ui.Adapter.CategorySpinnerAdapter;
import com.buildyourevent.buildyourevent.ui.Adapter.SubCategorySpinnerAdapter;
import com.buildyourevent.buildyourevent.ui.location.MapsActivity;
import com.buildyourevent.buildyourevent.ui.Adapter.CitiesAdapter;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UpdateProductActivity extends AppCompatActivity implements OnMapReadyCallback
{
    @BindView(R.id.editproduct_imageview) ImageView ivProduct;
    @BindView(R.id.chooseeditproduct_imageview) ImageView ivChhoseProduct;

    @BindView(R.id.editproductname_textview)
    EditText etProductName;
    @BindView(R.id.editproductdescription_textview) EditText etProductDesc;
    @BindView(R.id.editproductstatus_textview) EditText etProductStatus;
    @BindView(R.id.editproductprice_textview) EditText etProductPrice;
    @BindView(R.id.editproductstock_textview) EditText etProductStock;

    @BindView(R.id.editproduct_categoryspinner) Spinner spinnerCategory;
    @BindView(R.id.editproduct_subcategoryspinner) Spinner spinnerSubCategory;
    @BindView(R.id.editproductcity_spinner) Spinner spinnerCities;

    @BindView(R.id.editproduct_qtytext) TextView tvQuantity;

    @BindView(R.id.editproduct_startdaytext) TextView tvStartDay;
    @BindView(R.id.editproduct_startmonthtext) TextView tvStartMonth;
    @BindView(R.id.editproduct_startyeartext) TextView tvStartYear;
    @BindView(R.id.editproduct_enddaytext) TextView tvEndDay;
    @BindView(R.id.editproduct_endmonthtext) TextView tvEndMonth;
    @BindView(R.id.editproduct_endyeartext) TextView txEndYear;

    @BindView(R.id.editproduct_progressbar) ProgressBar progressBar;

    int qtyCounter = 0;

    static Bitmap bitmapPhoto;
    private int PICK_IMAGE_REQUEST = 1;
    MultipartBody.Part pic = null;
    File imageFile;

    private Calendar calendar;
    private int startDay, startMonth, startYear, endDay, endMonth, endYear;
    private DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    UserViewModel viewModel;
    UserData userData;
    SharedPrefMethods prefMethods;
    SubCategoryData subCategoryData;

    double lat, log;
    String address;

    FusedLocationProviderClient mFusedLocationClient;

    String startDate, endDate;
    String cityName;
    List<CityData> citiesList = new ArrayList<>();

    private GoogleMap mMap;

    List<CategoryData> categoriesList = new ArrayList<>();
    List<SubCategoryData> subCategoryList = new ArrayList<>();

    UserOwnProductData prodctInfo;
    ProductDetailsData productData;
    private int categoryId, subCategoryId;
    private String dayDate, monthDate, yearDate;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        ButterKnife.bind(this);

        prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, this.getBaseContext().getResources().getDisplayMetrics());

        getLocation();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d(Codes.APP_TAGS, "GPS is Enabled in your devide");
        } else {
            showGPSDisabledAlertToUser();
        }

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Log.d(Codes.APP_TAGS, "product details Id: " + prefMethods.getProductId());

        if (prefMethods.getSubCategoryData() != null)
        {
            subCategoryData = prefMethods.getSubCategoryData();
        }

        if (prefMethods.getUserData() != null)
        {
            userData = prefMethods.getUserData();
        }

        tvQuantity.setText("" + 0);

        calendar = Calendar.getInstance();
        if (userData != null) {
            getCities(userData.getCountryId());
        }

        viewModel.getProductDetails(prefMethods.getProductId()).observe(this, new Observer<ProductDetailsResponse>() {
            @Override
            public void onChanged(ProductDetailsResponse response)
            {
                productData = response.getData();
                setDataToFields();
            }
        });

        viewModel.getAllCategories().observe(this, (Observer<CategoryResponse>) categoryResponse -> {
            categoriesList = (ArrayList<CategoryData>) categoryResponse.getData();
            if (categoriesList.size() != 0)
            {
                buildCategoriesSpinner();
            }
        });

    }

    private void buildCategoriesSpinner()
    {
        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1, categoriesList);
        spinnerCategory.setAdapter(categorySpinnerAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                categoryId = categoriesList.get(pos).getId();
                Log.e(TAG, "onItemSelected: " + categoriesList.get(pos).getId());
                getSybCategories(categoryId);
                Log.d(Codes.APP_TAGS, "countries size: " +  categoriesList.size());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void getSybCategories(int categoryId)
    {
        viewModel.getAllSubCategories(categoryId).observe(this, new Observer<SubCategoryResponse>()
        {
            @Override
            public void onChanged(SubCategoryResponse response)
            {
                subCategoryList = (ArrayList<SubCategoryData>) response.getData();
                if (categoriesList.size() != 0)
                {
                    buildSubCategorySpinner();
                }
            }
        });
    }

    public void buildSubCategorySpinner()
    {
        SubCategorySpinnerAdapter subCategorySpinnerAdapter = new SubCategorySpinnerAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1, subCategoryList);
        spinnerSubCategory.setAdapter(subCategorySpinnerAdapter);
        spinnerSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                subCategoryId = subCategoryList.get(pos).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void setDataToFields()
    {
        Glide.with(this).load(productData.getImage()).into(ivProduct);

        etProductName.setText(productData.getName());
        etProductPrice.setText(productData.getPrice());
        etProductStatus.setText(productData.getStatus());
        etProductDesc.setText(productData.getDescription());
        etProductStock.setText("" + productData.getCurrentStock());
        tvQuantity.setText("" + productData.getNewAvailableQty());

        startDate = productData.getAvailableDate();
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


    @OnClick(R.id.chooseeditproduct_imageview)
    void ChoseProductImage(View v)
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
            ivProduct.setImageBitmap(bitmapPhoto);
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

    @OnClick(R.id.btneditproduct_selectlocation)
    void selectLocation(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();

        List<Double> list = new ArrayList<>();
        list.add(lat);
        list.add(log);
        prefMethods.saveUserCandidates(list);
    }

    @OnClick(R.id.editproduct_button)
    void addProduct(View view)
    {
         if (etProductName.getText().toString().isEmpty())
        {
            etProductName.setError("please enter product name");
        }
        else if (etProductDesc.getText().toString().isEmpty())
        {
            etProductDesc.setError("please enter product description");
        }
        else if (etProductStatus.getText().toString().isEmpty())
        {
            etProductStatus.setError("please enter product status");
        }
        else if (etProductStock.getText().toString().isEmpty())
        {
            etProductStock.setError("please enter product stock");
        }
        else if (etProductPrice.getText().toString().isEmpty())
        {
            etProductPrice.setError("please enter product price");
        }
        else if (pic == null)
         {
             Toast.makeText(this, "please enter product image", Toast.LENGTH_SHORT).show();
         }
        else
        {
            UpdateProduct();
        }
    }

    private void UpdateProduct()
    {
        progressBar.setVisibility(View.VISIBLE);
        startDate = yearDate + "/" + monthDate + "/" + dayDate;

        try {
            address = getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userData.getId()));
        RequestBody user_token = RequestBody.create(MediaType.parse("text/plain"), userData.getToken());
        RequestBody product_name = RequestBody.create(MediaType.parse("text/plain"), etProductName.getText().toString());
        RequestBody product_price = RequestBody.create(MediaType.parse("text/plain"), etProductPrice.getText().toString());
        RequestBody product_stock = RequestBody.create(MediaType.parse("text/plain"), etProductStock.getText().toString());
        RequestBody start_date = RequestBody.create(MediaType.parse("text/plain"), startDate);
        RequestBody product_quantity = RequestBody.create(MediaType.parse("text/plain"), tvQuantity.getText().toString());
        RequestBody product_status = RequestBody.create(MediaType.parse("text/plain"), etProductStatus.getText().toString());
        RequestBody product_desc = RequestBody.create(MediaType.parse("text/plain"), etProductDesc.getText().toString());
        RequestBody category_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productData.getCatId()));
        RequestBody subCategory_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productData.getSubCatId()));
        RequestBody productId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productData.getProductId()));
        RequestBody city_name = RequestBody.create(MediaType.parse("text/plain"), cityName);

        viewModel.updateProduct(pic, user_id, user_token,productId, product_name, product_price, product_stock,
                start_date, product_quantity, product_status, product_desc,
                category_id, subCategory_id, city_name).observe(this, new Observer<UpdateProductResponse>()
        {
            @Override
            public void onChanged(UpdateProductResponse updateProductResponse)
            {
                Log.d(Codes.APP_TAGS, "update product // " + updateProductResponse.getMessage());
                if (updateProductResponse.getStatus() == 200)
                {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(), MyProductsActivity.class);
                    startActivity(intent);
                    Toast.makeText(UpdateProductActivity.this, "done // " + updateProductResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UpdateProductActivity.this, "failed // " + updateProductResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Optional
    @OnClick({R.id.editproduct_startdaytext, R.id.editproduct_startmonthtext, R.id.editproduct_startyeartext})
    void selectStartDate(View view) {
        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //calendar.set(1, dayOfMonth);

                String myFormat = "MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String dateFormat = sdf.format(calendar.getTime());

                if (dateFormat.length() > 0) {
                    String[] mthYr = dateFormat.toString().split("/");
                    monthDate = String.valueOf(mthYr[0]);
                }

                dayDate = String.valueOf(dayOfMonth);
                yearDate = String.valueOf(year);

                tvStartDay.setText(dayDate);
                tvStartMonth.setText(monthDate);
                tvStartYear.setText( yearDate);

                Log.d(Codes.APP_TAGS, "" + startDay);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.show();
    }

    @Optional
    @OnClick({R.id.editproduct_enddaytext, R.id.editproduct_endmonthtext, R.id.editproduct_endyeartext})
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
                //  checkProductValidationDate();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endTime.show();
    }

    @OnClick(R.id.editproduct_incrementqty)
    void incrementQty(View v) {
        qtyCounter++;
        tvQuantity.setText("" + qtyCounter);
    }

    @OnClick(R.id.editproduct_decrementqty)
    void decrementQty(View v) {
        if (qtyCounter != 0) {
            qtyCounter--;
            tvQuantity.setText("" + qtyCounter);
        }
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public DatePickerDialog.OnDateSetListener getDate() {
        return startDatePicker;
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

    public void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        String provider = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }

        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>()
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

                           mFusedLocationClient = LocationServices.getFusedLocationProviderClient(UpdateProductActivity.this);
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

    public void checkProductValidationDate() throws ParseException
    {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse(startDay + "-" + startMonth + "-" + startYear);
        Date d2 = sdformat.parse(endDay + "-" + endMonth + "-" + endYear);

        if(d1.compareTo(d2) > 0)
        {
            Toast.makeText(this, "Date 1 occurs after Date 2", Toast.LENGTH_SHORT).show();
        }
        else if(d1.compareTo(d2) < 0)
        {
            System.out.println("Date 1 occurs before Date 2");
        }
        else if(d1.compareTo(d2) == 0)
        {
            System.out.println("Both dates are equal");
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    public String removeSpaces()
    {
        String str = productData.getAvailableDate();
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("[\\[\\](){}]","");

        return str;
    }

}

