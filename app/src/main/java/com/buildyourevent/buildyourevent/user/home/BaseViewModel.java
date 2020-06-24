package com.buildyourevent.buildyourevent.user.home;

import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel implements Observable
{
    private MutableLiveData<Object> mutableLiveData;
    private PropertyChangeRegistry mCallBacks;


    public BaseViewModel() {
        mCallBacks = new PropertyChangeRegistry();
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Object> getMutableLiveData() {
        return mutableLiveData == null ? mutableLiveData = new MutableLiveData<>() : mutableLiveData;
    }

    public void setValue(Object item) {
        mutableLiveData.setValue(item);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        mCallBacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        mCallBacks.remove(callback);
    }

    public void notifyChange() {
        mCallBacks.notifyChange(this, 0);
    }

    public void notifyChange(int propertyId) {
        mCallBacks.notifyChange(this, propertyId);
    }


}
