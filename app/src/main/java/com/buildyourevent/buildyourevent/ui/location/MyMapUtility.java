package com.buildyourevent.buildyourevent.ui.location;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.utils.BaseApp;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.patloew.rxlocation.RxLocation;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

import static android.content.Context.LOCATION_SERVICE;

public abstract class MyMapUtility {

    public static void showCurrentLocationBtn(@NonNull GoogleMap mMap, boolean show) {
        try {
            if (show)
                mMap.setMyLocationEnabled(true);
            else
                mMap.setMyLocationEnabled(false);
        } catch (SecurityException e) {
         //   Timber.e(e);
        }
    }

    public static void showZoomBtn(@NonNull GoogleMap mMap, boolean show) {
        if (show)
            mMap.getUiSettings().setZoomControlsEnabled(true);
        else
            mMap.getUiSettings().setZoomControlsEnabled(false);
    }

    public static void showCompassBtn(GoogleMap mMap, boolean show) {
        if (show)
            mMap.getUiSettings().setCompassEnabled(true);
        else
            mMap.getUiSettings().setCompassEnabled(false);
    }

    //CHECK FOR LOCATION Requirements
    private static void checkForGpsEnabled(Activity activity, MutableLiveData<Boolean> liveData) {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build());
        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                // All location settings are satisfied. The client can initialize location
                // requests here.
                liveData.setValue(true);
            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(activity, Codes.GPS_SETTINGS_REQ_CODE);
                        } catch (IntentSender.SendIntentException ignore) {
                            MovementManager.openGPSSetting(activity);
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        MovementManager.openGPSSetting(activity);
                        break;
                }
            }
        });
    }

    public static boolean hasLocationPermissions(FragmentActivity activity) {
        return PermissionManager.isGranted(activity, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static void hasAllLocationReq(Activity activity, MutableLiveData<Boolean> liveData) {
        if (checkForGoogleApiAvailability(activity)) {
            checkForGpsEnabled(activity, liveData);
        }
    }
    public static boolean isGPSEnabled(Context context)
    {
        LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        assert manager != null;
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    private static boolean checkForGoogleApiAvailability(Activity activity) {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int status = apiAvailability.isGooglePlayServicesAvailable(BaseApp.getInstance());
        if (status != ConnectionResult.SUCCESS) {
            if (!apiAvailability.isUserResolvableError(status)) {
                Snackbar snackbar = Snackbar.make(AppUtil.getRootView(activity),
                        "Google Play Services unavailable. This app will not work",
                        Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("close", v -> snackbar.dismiss());
                snackbar.show();
                return false;
            } else {
                Toast.makeText(activity, "error: " + status, Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            // Timber.e("has GooglePlay service");
            return true;
        }
    }

    public static Bitmap setMarkerIcon(int drawableRes) {
        Drawable drawable = BaseApp.getInstance().getApplicationContext().getResources()
                .getDrawable(drawableRes);
        return AppUtil.drawableToBitmap(drawable);
    }

    public static void openGoogleMapsForDirection(String lat, String lng, Activity activity) {
        //ex: https://www.google.com/maps/search/?api=1&query=47.5951518,-122.3316393
        StringBuilder builder = new StringBuilder("https://www.google.com/maps/search/?");
        builder.append("api=1&")
                .append("query=")
                .append(lat)
                .append(",")
                .append(lng);
      //  Timber.e(builder.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(builder.toString()));
        intent.setPackage("com.google.android.apps.maps");
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(builder.toString()));
                activity.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
              //  Timber.e("Please install a maps application");
            }
        }
    }

    public static MarkerOptions getMarkerOptions(String title, LatLng latLng, BitmapDescriptor icon) {
        MarkerOptions options = new MarkerOptions();
        options.title(title)
                .position(latLng)
                .icon(icon);
        return options;
    }

    public static MarkerOptions getMarkerOptions(String title, LatLng latLng, float defaultIcon) {
        MarkerOptions options = new MarkerOptions();
        options.title(title)
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(defaultIcon));
        return options;
    }

    public static MarkerOptions getMarkerOptions(String title, LatLng latLng) {
        MarkerOptions options = new MarkerOptions();
        options.title(title)
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(getRandomDefaultCursor()));
        return options;
    }


    private static ArrayList<Float> getCursorsList() {
        ArrayList<Float> list = new ArrayList<>();
        list.add(BitmapDescriptorFactory.HUE_RED);
        list.add(BitmapDescriptorFactory.HUE_BLUE);
        list.add(BitmapDescriptorFactory.HUE_ORANGE);
        list.add(BitmapDescriptorFactory.HUE_ROSE);
        list.add(BitmapDescriptorFactory.HUE_CYAN);
        list.add(BitmapDescriptorFactory.HUE_GREEN);
        list.add(BitmapDescriptorFactory.HUE_MAGENTA);
        list.add(BitmapDescriptorFactory.HUE_AZURE);
        list.add(BitmapDescriptorFactory.HUE_VIOLET);
        list.add(BitmapDescriptorFactory.HUE_YELLOW);
        return list;
    }

    private static int getRandomIntBetweenRange(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    public static float getRandomDefaultCursor() {
        ArrayList<Float> list = getCursorsList();
        if (list != null && list.size() > 0)
            return list.get(getRandomIntBetweenRange(0, list.size() - 1));
        else
            return BitmapDescriptorFactory.HUE_RED;
    }

    public static void animateCameraToPosition(GoogleMap googleMap, LatLng currentLatLng, boolean animate) {
        CameraUpdate factory = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
        if (animate)
            googleMap.animateCamera(factory);
        else {
            googleMap.moveCamera(factory);
        }
    }

  /*  public static Disposable getLocationUpdates(RxLocation rxLocation, ObservableField<String> obsAddress, ObservableField<LatLng> obsLatlng) {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000);
        return rxLocation.location().updates(locationRequest)
                .flatMap(location -> rxLocation.geocoding().fromLocation(location).toObservable())
                .subscribe(address -> {
                    Timber.e("address here: " + address.getAddressLine(0));
                    obsAddress.set(address.getAddressLine(0));
                });
    }
*/
    public static void enableTouchScrolling(GoogleMap googleMap, boolean enable) {
        googleMap.getUiSettings().setScrollGesturesEnabled(enable);
        googleMap.getUiSettings().setZoomGesturesEnabled(enable);
        if (!enable)
            googleMap.setOnMapClickListener(null);
    }

    public static void setPinToCurrentLocation(GoogleMap googleMap, RxLocation rxLocation, ObservableField<LatLng> obsLatLng) {
        Disposable disposable = rxLocation.location().lastLocation().subscribe(location -> {
            googleMap.clear();
            obsLatLng.set(new LatLng(location.getLatitude(), location.getLongitude()));
            googleMap.addMarker(getMarkerOptions("", obsLatLng.get(), getRandomDefaultCursor()));
            animateCameraToPosition(googleMap, obsLatLng.get(), true);
        });
    }

 /*   public static void setPinToCurrentLocation(GoogleMap googleMap, RxLocation rxLocation, boolean animateCamera) {
        Disposable disposable = rxLocation.location().lastLocation().subscribe(location -> {
            googleMap.clear();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.addMarker(getMarkerOptions("", latLng, getRandomDefaultCursor()));
            animateCameraToPosition(googleMap, latLng, animateCamera);
        });
    }

    public static void setPinToCurrentLocationWithAddress(GoogleMap googleMap, RxLocation rxLocation, ObservableField<LatLng> obsLatLng, ObservableField<String> obsAddress) {
        Disposable disposable = rxLocation.location().lastLocation().subscribe(location -> {
            getAddress(rxLocation, location, obsAddress);
            googleMap.clear();
            obsLatLng.set(new LatLng(location.getLatitude(), location.getLongitude()));
            googleMap.addMarker(getMarkerOptions(obsAddress.get(), obsLatLng.get(), getRandomDefaultCursor()));
            animateCameraToPosition(googleMap, obsLatLng.get(), true);
        });
    }*/

    /*public static Maybe<Location> getCurrentLocation(RxLocation rxLocation) {
        if (ActivityCompat.checkSelfPermission(geA, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        return rxLocation.location().lastLocation();
    }*/

    public static void setPinToLocation(GoogleMap googleMap, Marker currentMarker, LatLng currentLatLng, boolean animateCamera) {
        currentMarker.setPosition(currentLatLng);
        animateCameraToPosition(googleMap, currentLatLng, animateCamera);
    }

    public static void setPinToLocation(GoogleMap googleMap, LatLng currentLatLng, boolean animateCamera) {
        googleMap.clear();
        googleMap.addMarker(getMarkerOptions("", currentLatLng));
        animateCameraToPosition(googleMap, currentLatLng, animateCamera);
    }

    public static void setPinToLocationWithAddress(RxLocation rxLocation, GoogleMap googleMap, LatLng currentLatLng, boolean animateCamera, ObservableField<String> obsAddress) {
        googleMap.clear();
        getAddress(rxLocation, currentLatLng, obsAddress);
        googleMap.addMarker(getMarkerOptions(obsAddress.get(), currentLatLng));
        animateCameraToPosition(googleMap, currentLatLng, animateCamera);
    }



    public static void getAddress(RxLocation rxLocation, LatLng latLng, ObservableField<String> obsAddress) {
        Disposable disposable = rxLocation.geocoding().fromLocation(latLng.latitude, latLng.longitude, 1)
                .subscribe(addresses -> {
                    obsAddress.set(addresses.get(0).getAddressLine(0));
                });
    }

    public static void getAddress(RxLocation rxLocation, Location location, ObservableField<String> obsAddress) {
        Disposable disposable = rxLocation.geocoding().fromLocation(location)
                .subscribe(addresses -> {
                    obsAddress.set(addresses.getAddressLine(0));
                });
    }
}

