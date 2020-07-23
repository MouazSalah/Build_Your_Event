package com.buildyourevent.buildyourevent.ui.location;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patloew.rxlocation.RxLocation;

import io.reactivex.disposables.CompositeDisposable;

public class MapsViewModel extends ViewModel
{
    private RxLocation rxLocation;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private GoogleMap mMap;

    public LatLng latLng;
    public String myAddress;

    public MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();

    public MapsViewModel(RxLocation rxLocation)
    {
        this.rxLocation = rxLocation;
    }
    public LocationRequest getLocationRequest(int interval)
    {
        return LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(interval);
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation()
    {
//        show progreess bar
        mDisposable.add(rxLocation.location().updates(getLocationRequest(1000))
                .flatMap(location -> rxLocation.geocoding().fromLocation(location).toObservable())
                .subscribe(address -> {
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));

                   // mutableLiveData.setValue(Codes.ADDRESS_SAVED);
                    myAddress = address.getCountryName();


//                    mutableLiveData.setValue(Codes.GET_ADDRESS);
//                    hide progressbar
                    //do what u want with address obj
                    /*
                    * if you want only current location ONE time only
                    * */
                   // mDisposable.clear();   //run this code
                })
        );
    }
    public void isMapReady(GoogleMap googleMap)
    { //All clear do your staff here
        this.mMap = googleMap;

        getCurrentLocation();

    }
    @Override
    protected void onCleared() {
        mDisposable.clear();   //remove listen to location
        super.onCleared();
    }

    public void saveLocation()
    {
        mutableLiveData.setValue(Codes.ADDRESS_SAVED);
    }
}
