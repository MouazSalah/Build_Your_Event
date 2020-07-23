package com.buildyourevent.buildyourevent.ui.location;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.ActivityAddressBinding;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patloew.rxlocation.RxLocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

public class AddressActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private RxLocation rxLocation;
    private MutableLiveData<Boolean> gpsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isMapReady = new MutableLiveData<>();
    private SupportMapFragment mapFragment;
    private MapsViewModel viewModel;
    ActivityAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        rxLocation = new RxLocation(this);
        viewModel = new MapsViewModel(rxLocation);

        binding.setModel(viewModel);

        MyMapUtility.hasAllLocationReq(AddressActivity.this, gpsMutableLiveData);  //here you start everything

        gpsMutableLiveData.observe(this, isOpen -> {
            Log.e("TAG", "onCreate: " + isOpen );
            if (isOpen) {
                checkForPermission();
            }
        });
        isMapReady.observe(this, isReady -> {
            if (isReady) {
                Log.d("map", "map is ready");
                assert mapFragment != null;
                mapFragment.getMapAsync(this);
            }
        });

        viewModel.mutableLiveData.observe(this, integer ->
        {
            if (integer == Codes.ADDRESS_SAVED)
            {
                Intent intent = new Intent();
                try {

                    intent.putExtra("address",getAddress());

                    Codes.USER_ADDRESS = getAddress();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Log.d("Mou3aaaz", "maps activity: "+ getAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                setResult(integer, intent);
                finish();
            }
        });

    }

    private void checkForPermission() {
        if (!MyMapUtility.hasLocationPermissions(this)) {
            mDisposable.add(PermissionManager.request(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe(isGranted -> {
                        if (isGranted) {
                            isMapReady.setValue(true);
                        }
                    }));
        } else {
            isMapReady.setValue(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("mou3aaz", "GPS not enabled yet" + resultCode + " " + requestCode);

        if (requestCode == Codes.GPS_SETTINGS_REQ_CODE) {

            if (MyMapUtility.isGPSEnabled(this)) {
                checkForPermission();
            } else {
                Log.d("mou3aaz", "GPS not enabled yet");
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        viewModel.isMapReady(googleMap); // now the view is Clear

        /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
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
        });*/
    }


    public String getAddress() throws IOException
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        double latitude = viewModel.latLng.latitude;
        double longitude = viewModel.latLng.longitude;

        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        // return address + " / " + state + " / " + country + " / " + city + " / " + postalCode + " / " + knownName;
        return city + ", " + state +", " + country;
    }

}