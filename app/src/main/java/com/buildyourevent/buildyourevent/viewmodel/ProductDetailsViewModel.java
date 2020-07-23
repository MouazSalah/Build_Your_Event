package com.buildyourevent.buildyourevent.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartResponse;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartsRequest;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsData;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsResponse;

public class ProductDetailsViewModel extends ViewModel
{
    public ProductDetailsData productData;
    ProductRepository productRepository = new ProductRepository();

    public ProductDetailsViewModel()
    {
        getProductData();
    }

    private void getProductData()
    {
        productRepository.getProductDetails(1).observeForever(new Observer<ProductDetailsResponse>()
        {
            @Override
            public void onChanged(ProductDetailsResponse productDetailsResponse)
            {
                productData = productDetailsResponse.getData();
            }
        });
    }

    public LiveData<ProductDetailsResponse> getProductData(int productId)
    {
        return productRepository.getProductDetails(productId);
    }

    public LiveData<CartResponse> getAllCarts(int id, String token)
    {
        return productRepository.getCartsMutableLiveData(id, token);
    }

    public LiveData<AddToCartResponse> addToCarts(AddToCartsRequest addToCartsRequest)
    {
        return productRepository.getAddToCartsMutableLiveData(addToCartsRequest);
    }
}
