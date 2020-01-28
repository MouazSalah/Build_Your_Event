package com.buildyourevent.buildyourevent.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartResponse;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartsRequest;
import com.buildyourevent.buildyourevent.model.data.banner.BannerData;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.model.data.category.CategoryData;
import com.buildyourevent.buildyourevent.model.data.order.OrderRequest;
import com.buildyourevent.buildyourevent.model.data.order.OrderResponse;
import com.buildyourevent.buildyourevent.model.data.product.ProductData;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsData;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartRequest;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;

import java.util.List;

public class UserViewModel extends ViewModel
{
    private UserRepository userRepository;

    public UserViewModel()
    {
        userRepository = new UserRepository();
    }

    public LiveData<List<BannerData>> getAllBanners()
    {
        return userRepository.getBannersMutableLiveData();
    }


    public LiveData<List<CategoryData>> getAllCategories()
    {
        return userRepository.getCategoryMutableLiveData();
    }

    public LiveData<List<SubCategoryData>> getAllSubCategories(int id)
    {
        return userRepository.getSubCategoryMutableLiveData(id);
    }


    public LiveData<List<ProductData>> getAllProducts(int subCategoryId)
    {
        return userRepository.getProductsMutableLiveData(subCategoryId);
    }

    public LiveData<ProductDetailsData> getProductDetails(int productId)
    {
        /*userRepository.getProductDetails(productId);
        return userRepository.productDetailsMutableLiveData;*/
        return userRepository.getProductDetails(productId);
    }


    public LiveData<CartResponse> getAllCarts(int id, String token)
    {
        return userRepository.getCartssMutableLiveData(id, token);
    }


    public LiveData<AddToCartResponse> addToCarts(AddToCartsRequest addToCartsRequest)
    {
        return userRepository.getAddToCartsMutableLiveData(addToCartsRequest);
    }

    public LiveData<RemoveCartResponse> removeFromCart(RemoveCartRequest removeCartRequest)
    {
        return userRepository.getRemoveCartMutableLiveData(removeCartRequest);
    }

    public LiveData<OrderResponse> confirmOrder(OrderRequest orderRequest)
    {
        return userRepository.getConfirmOrderMutableLiveData(orderRequest);
    }
}
