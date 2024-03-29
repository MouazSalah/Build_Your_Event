package com.buildyourevent.buildyourevent.ui.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.ui.userproducts.AddProductActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.patloew.rxlocation.RxLocation;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback, LocationSource.OnLocationChangedListener {

    private GoogleMap mMap;
    double lat, log;
    FusedLocationProviderClient mFusedLocationClient;
    LocationManager mLocationManager;
    SharedPrefMethods prefMethods;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Location myLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPrefMethods prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_fragment);
        mapFragment.getMapAsync(this);

        getLocation();

        RxLocation rxLocation = new RxLocation(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        rxLocation.location().lastLocation().doOnSuccess(new Consumer<Location>() {
            @Override
            public void accept(Location location) throws Exception {
                lat = location.getLatitude();
                log = location.getLongitude();
                Toast.makeText(LocationActivity.this, "location get", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.clear();

        prefMethods = new SharedPrefMethods(this);
        List<Double> listLatLng = prefMethods.getUserCandidates();
        lat = listLatLng.get(0);
        log = listLatLng.get(1);

        LatLng userLocation = new LatLng(lat, log);

        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15.0f));

        getLocation();

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
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


    public void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().
                addOnCompleteListener(new OnCompleteListener<Location>() {
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

    @OnClick(R.id.button_selectLocation)
    void selectLocation(View v)
    {
        Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        lat = location.getLatitude();
        log = location.getLongitude();
    }
}
