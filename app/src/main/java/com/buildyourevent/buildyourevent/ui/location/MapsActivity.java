package com.buildyourevent.buildyourevent.ui.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
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
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.ui.products.ProductInfoActivity;
import com.buildyourevent.buildyourevent.ui.userproducts.UpdateProductActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
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
import com.patloew.rxlocation.RxLocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    double lat, log;
    FusedLocationProviderClient mFusedLocationClient;
    SharedPrefMethods prefMethods;
    LatLng userLocation;
    String provider;
    LocationManager locationManager;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

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
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
           // checkLocationPermission();

             getLocation();
            Log.d(Codes.APP_TAGS, "GPS is Enabled in your devide");
        } else {
            showGPSDisabledAlertToUser();
        }

        userLocation = new LatLng(lat, log);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        RxLocation rxLocation = new RxLocation(this);
        rxLocation.location().lastLocation().doOnSuccess(new Consumer<Location>()
        {
            @Override
            public void accept(Location location) throws Exception
            {
                lat = location.getLatitude();
                log = location.getLongitude();
                Toast.makeText(MapsActivity.this, "location get", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        mMap.clear();

        //getLocation();

        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15.0f));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            public void onMapClick(LatLng point)
            {
                Toast.makeText(getApplicationContext(), point.latitude + ", " + point.longitude,
                        Toast.LENGTH_SHORT).show();

                mMap.clear();
                LatLng userLocation = new LatLng(point.latitude, point.longitude);
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15.0f));
            }
        });
    }

    public void  getLocation()
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps"))
        { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }

        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>()
                 {
                     @Override
                     public void onComplete(@NonNull Task<Location> task)
                     {
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
                         }
                         else
                         {
                             lat = location.getLatitude();
                             log = location.getLongitude();
                         }
                     }
                 }
        );
    }


    @OnClick(R.id.select_location_button)
    void selectLocation(View v)
    {
        Intent intent = new Intent(getApplicationContext(), ProductInfoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), ProductInfoActivity.class);
        startActivity(intent);
        finish();
    }



    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION))
            {
                showGPSDisabledAlertToUser();
            }
            else
             {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)
                    {
                        getLocation();

                        //Request location updates:
                       // locationManager.requestLocationUpdates(provider, 400, 1,  this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

           // locationManager.removeUpdates((LocationListener) this);
        }
    }
}
