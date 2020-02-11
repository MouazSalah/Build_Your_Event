package com.buildyourevent.buildyourevent.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordRequest;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordResponse;
import com.buildyourevent.buildyourevent.model.auth.cities.CityData;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.code.VerifyCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;
import com.buildyourevent.buildyourevent.model.auth.login.LoginRequest;
import com.buildyourevent.buildyourevent.model.auth.login.LoginResponse;
import com.buildyourevent.buildyourevent.model.auth.logout.LogoutRequest;
import com.buildyourevent.buildyourevent.model.auth.logout.LogoutResponse;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.auth.resetpassword.ResetPasswordResponse;
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

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserViewModel extends ViewModel
{
    private AuthRepository authRepository;
    private UserRepository userRepository;

    private MutableLiveData<Object> viewModelLiveData = new MutableLiveData<>();


    public UserViewModel()
    {
        userRepository = new UserRepository();
        authRepository = new AuthRepository();
    }


    public LiveData<LoginResponse> loginCurrentUser(LoginRequest loginRequest)
    {
        return authRepository.loginUser(loginRequest);
    }

    public LiveData<RegisterResponse> registerUser(MultipartBody.Part pic, RequestBody toString, RequestBody toString1, RequestBody toString2,
                                                   RequestBody toString3, RequestBody countryId, RequestBody cityId)
    {
        return authRepository.registerNewUser(pic, toString,  toString1,  toString2,
                toString3,  countryId,  cityId);
    }

    public LiveData<List<CountryData>> getAllCountries()
    {
        return authRepository.getCountriesMutableLiveData();
    }

    public LiveData<List<CityData>> getAllCities(int countryId)
    {
        return authRepository.getCitiesMutableLiveData(countryId);
    }

    public LiveData<LogoutResponse> logoutUser(LogoutRequest logoutRequest)
    {
        return authRepository.logoutUser(logoutRequest);
    }

    public LiveData<SendCodeResponse> sendCode(String email)
    {
        return authRepository.getSendCodeMutableLiveData(email);
    }

    public LiveData<VerifyCodeResponse> verifyCode(String email, int code)
    {
        return authRepository.getVerifyCodeResponseMutableLiveData(email, code);
    }

    public LiveData<ResetPasswordResponse> resetPassword(String email, String password)
    {
        return authRepository.getResetPasswordResponseMutableLiveData(email, password);
    }

    public LiveData<ChangePasswordResponse> changePassword(ChangePasswordRequest changePasswordRequest)
    {
        return authRepository.getChangePasswordMutableLiveData(changePasswordRequest);
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


    public LiveData<List<ProductData>> getAllProducts(String subCategoryId)
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
