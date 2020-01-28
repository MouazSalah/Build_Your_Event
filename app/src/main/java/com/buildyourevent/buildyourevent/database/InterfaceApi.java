package com.buildyourevent.buildyourevent.database;

import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordRequest;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordResponse;
import com.buildyourevent.buildyourevent.model.auth.code.VerifyCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartResponse;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartsRequest;
import com.buildyourevent.buildyourevent.model.data.banner.BannerResponse;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.model.data.category.CategoryResponse;
import com.buildyourevent.buildyourevent.model.auth.cities.CityResponse;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.countries.CountryResponse;
import com.buildyourevent.buildyourevent.model.auth.login.LoginRequest;
import com.buildyourevent.buildyourevent.model.auth.login.LoginResponse;
import com.buildyourevent.buildyourevent.model.auth.logout.LogoutRequest;
import com.buildyourevent.buildyourevent.model.auth.logout.LogoutResponse;
import com.buildyourevent.buildyourevent.model.data.order.OrderRequest;
import com.buildyourevent.buildyourevent.model.data.order.OrderResponse;
import com.buildyourevent.buildyourevent.model.data.product.ProductResponse;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterRequest;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsResponse;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartRequest;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartResponse;
import com.buildyourevent.buildyourevent.model.auth.resetpassword.ResetPasswordResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InterfaceApi
{
    @POST("login")
    Call<LoginResponse> loginToAccount(@Body LoginRequest loginRequest);

    @POST("registeration")
    Call<RegisterResponse> createNewUser(@Body RegisterRequest registerRequest);

    @GET("countries")
    Call<CountryResponse> getAllCountries();

    @FormUrlEncoded
    @POST("cities")
    Call<CityResponse> getAllCities(@Field("country_id") int country_id);

    @POST("logout")
    Call<LogoutResponse> logout(@Body LogoutRequest logoutRequest);

    @GET("banners")
    Call<BannerResponse> getAllBanners();

    @GET("categories")
    Call<CategoryResponse> getAllCategories();

    @FormUrlEncoded
    @POST("sub-category")
    Call<SubCategoryResponse> getAllSubCategories( @Field("category_id") int categoryId);

    @FormUrlEncoded
    @POST("products")
    Call<ProductResponse> getAllProducts(@Field("Cat_id") int subCategoryId);

    @FormUrlEncoded
    @POST("product_details")
    Call<ProductDetailsResponse> getProductDetails(@Field("product_id") int  product_id);

    @FormUrlEncoded
    @POST("cart")
    Call<CartResponse> getAllCarts(@Field("user_id") int  userId, @Field("api_token") String  apiToken);

    @POST("add-to-cart")
    Call<AddToCartResponse> addToCart(@Body AddToCartsRequest addToCartsRequest);

    @POST("remove_from_cart")
    Call<RemoveCartResponse> removeFromCart(@Body RemoveCartRequest removeCartRequest);

    @POST("confirm-order")
    Call<OrderResponse> confirmOrder(@Body OrderRequest orderRequest);

    @FormUrlEncoded
    @POST("send-code")
    Call<SendCodeResponse> sendCode(@Field("email") String email);

    @FormUrlEncoded
    @POST("verify-code")
    Call<VerifyCodeResponse> verifyCode(@Field("email") String email, @Field("code") int code);

    @FormUrlEncoded
    @POST("reset-password")
    Call<ResetPasswordResponse> resetPassword(@Field("email") String email, @Field("password") String password);


    @POST("update-password")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

}
