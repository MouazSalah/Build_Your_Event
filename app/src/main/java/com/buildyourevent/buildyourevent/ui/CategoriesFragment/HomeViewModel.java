package com.buildyourevent.buildyourevent.ui.CategoriesFragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buildyourevent.buildyourevent.database.UserRepository;

public class HomeViewModel extends ViewModel
{
    private UserRepository authRepository = new UserRepository();

    private MutableLiveData<Object> categoryMutableLiveData;
    private MutableLiveData<Object> bannerMutableLiveData;

    public HomeViewModel()
    {
        categoryMutableLiveData = new MutableLiveData<>();
        bannerMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Object> getCategoryMutableLiveData()
    {
        return categoryMutableLiveData;
    }

    public void setCategoryValueLiveData(Object value)
    {
        categoryMutableLiveData.setValue(value);
    }

    public Object getCategoryValueLiveData()
    {
        return categoryMutableLiveData.getValue();
    }


    public MutableLiveData<Object> getBannerMutableLiveData()
    {
        return bannerMutableLiveData;
    }

    public void setBannerValueLiveData(Object value)
    {
        bannerMutableLiveData.setValue(value);
    }

    public Object getBannerValueLiveData()
    {
        return bannerMutableLiveData.getValue();
    }


   /* public void getAllCategories()
    {
        authRepository.getAllCategories();
        setCategoryValueLiveData(authRepository.getCategoryValueLiveData());
    }*/

   /* public void getAllBanners()
    {
        authRepository.getAllBanners();
        setBannerValueLiveData(authRepository.getBannerValueLiveData());
    }*/
}