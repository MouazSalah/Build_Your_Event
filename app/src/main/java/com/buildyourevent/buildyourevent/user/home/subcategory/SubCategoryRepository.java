package com.buildyourevent.buildyourevent.user.home.subcategory;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.buildyourevent.buildyourevent.database.InterfaceApi;
import com.buildyourevent.buildyourevent.database.RetrofitClient;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.banner.BannerData;
import com.buildyourevent.buildyourevent.model.data.product.ProductResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryRepository
{
    InterfaceApi interfaceAp  = RetrofitClient.getApiClient(Codes.AUTH_BASE_URL).create(InterfaceApi.class);

    private MutableLiveData<SubCategoryResponse> subCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<ProductResponse> allProductsLiveData = new MutableLiveData<>();


    public MutableLiveData<SubCategoryResponse> getSubCategoryMutableLiveData(int categoryId)
    {
        Call<SubCategoryResponse> responseCall = interfaceApi.getAllSubCategories(categoryId);
        responseCall.enqueue(new Callback<SubCategoryResponse>()
        {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response)
            {
                if (response.isSuccessful())
                {
                    subCategoryLiveData.setValue(response.body());
                }
                else
                {

                }
            }
            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "sub category failed" + t.getMessage());
            }
        });

        return subCategoryLiveData;
    }

    public MutableLiveData<ProductResponse> getProductsMutableLiveData(String subCategoryId)
    {
        Call<ProductResponse> responseCall = interfaceApi.getAllProducts(subCategoryId);
        responseCall.enqueue(new Callback<ProductResponse>()
        {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response)
            {
                if (response.isSuccessful())
                {
                    allProductsLiveData.setValue(response.body());
                    Log.d(Codes.APP_TAGS, "product not success" + response.message());
                }
                else
                {

                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "products failed" + t.getMessage());
            }
        });

        return allProductsLiveData;
    }

    public MutableLiveData<ProductResponse> getAllProductsMutableLiveData()
    {
        Call<ProductResponse> responseCall = interfaceApi.getProducts();
        responseCall.enqueue(new Callback<ProductResponse>()
        {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response)
            {
                ProductResponse productResponse = response.body();
                allProductsLiveData.setValue(productResponse);
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "products failed" + t.getMessage());
            }
        });

        return allProductsLiveData;
    }

}
