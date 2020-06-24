package com.buildyourevent.buildyourevent.user.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.buildyourevent.buildyourevent.database.InterfaceApi;
import com.buildyourevent.buildyourevent.database.RetrofitClient;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartResponse;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartsRequest;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.model.data.product.ProductResponse;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProductRepository
{
    InterfaceApi interfaceApi = RetrofitClient.getApiClient(Codes.AUTH_BASE_URL).create(InterfaceApi.class);

    private MutableLiveData<ProductDetailsResponse> productLiveData = new MutableLiveData<>();
    private MutableLiveData<CartResponse> cartLiveData = new MutableLiveData<>();
    private MutableLiveData<AddToCartResponse> addToCartLiveData = new MutableLiveData<>();


    public MutableLiveData<ProductDetailsResponse> getProductDetails(int productId)
    {
        Call<ProductDetailsResponse> responseCall = interfaceApi.getProductDetails(productId);
        responseCall.enqueue(new Callback<ProductDetailsResponse>()
        {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response)
            {
                if (response.isSuccessful())
                {
                    productLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t)
            {
            }
        });
        return productLiveData;
    }

    public MutableLiveData<CartResponse> getCartsMutableLiveData(int userId, String apiToken)
    {
        Call<CartResponse> responseCall = interfaceApi.getAllCarts(userId, apiToken);
        responseCall.enqueue(new Callback<CartResponse>()
        {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response)
            {
                if (!response.isSuccessful())
                {
                    cartLiveData.setValue(response.body());
                }
                else
                {
                    cartLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<CartResponse> call, Throwable t)
            {
            }
        });

        return cartLiveData;  //this is wrong
    }

    public MutableLiveData<AddToCartResponse> getAddToCartsMutableLiveData(AddToCartsRequest addToCartsRequest)
    {
        Call<AddToCartResponse> responseCall = interfaceApi.addToCart(addToCartsRequest);
        responseCall.enqueue(new Callback<AddToCartResponse>()
        {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response)
            {
                if (response.isSuccessful())
                {
                    AddToCartResponse addToCartResponse = response.body();
                    addToCartLiveData.setValue(addToCartResponse);
                }
            }
            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t)
            {
            }
        });

        return addToCartLiveData;
    }

}
