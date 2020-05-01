package com.buildyourevent.buildyourevent.database;

import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordRequest;
import com.buildyourevent.buildyourevent.model.auth.change_password.ChangePasswordResponse;
import com.buildyourevent.buildyourevent.model.auth.code.VerifyCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.register.RegisterResponse;
import com.buildyourevent.buildyourevent.model.data.productrate.ProductRateRequest;
import com.buildyourevent.buildyourevent.model.data.aboutus.AboutUsResponse;
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
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsResponse;
import com.buildyourevent.buildyourevent.model.data.productrate.ProductRateResponse;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartRequest;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartResponse;
import com.buildyourevent.buildyourevent.model.auth.resetpassword.ResetPasswordResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;
import com.buildyourevent.buildyourevent.model.data.updateproduct.UpdateProductResponse;
import com.buildyourevent.buildyourevent.model.data.userproduct.request.RemoveProductRequest;
import com.buildyourevent.buildyourevent.model.data.userproduct.response.AddProductResponse;
import com.buildyourevent.buildyourevent.model.data.userproduct.response.RemoveProductResponse;
import com.buildyourevent.buildyourevent.model.data.userproduct.response.UserOwnProductResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InterfaceApi
{
    @POST("login")
    Call<LoginResponse> loginToAccount(@Body LoginRequest loginRequest);


    @Multipart
    @POST("registeration")
    Call<RegisterResponse> createNewUser(@Part MultipartBody.Part image,
                                         @Part("name") RequestBody name,
                                         @Part("email") RequestBody email,
                                         @Part("password") RequestBody password,
                                         @Part("country_id") RequestBody country_id,
                                         @Part("city_id") RequestBody city_id,
                                         @Part("mobile") RequestBody mobil);




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
    Call<ProductResponse> getAllProducts(@Field("sub_cat_id") String subCategoryId);

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


    @FormUrlEncoded
    @POST("update-password")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @FormUrlEncoded
    @POST("show_own_products")
    Call<UserOwnProductResponse> showOwnProducts(@Field("user_id") int userId, @Field("api_token") String apiToken);

    @Multipart
    @POST("add_product")
    Call<AddProductResponse> addOwnProducts(@Part MultipartBody.Part image,
                                            @Part("user_id") RequestBody userId, @Part("api_token") RequestBody apiToken,
                                            @Part("name") RequestBody password, @Part("price") RequestBody country_id,
                                            @Part("current_stock") RequestBody city_id, @Part("available_at") RequestBody startDate,
                                            @Part("new_available_qty") RequestBody availQty, @Part("status") RequestBody status,
                                            @Part("description") RequestBody desc, @Part("cat_id") RequestBody categoryId,
                                            @Part("sub_cat_id") RequestBody subCategoryId, @Part("location") RequestBody location);


    @POST("remove_product")
    Call<RemoveProductResponse> removeProduct(@Body RemoveProductRequest removeProductRequest);

    @FormUrlEncoded
    @POST("update-product")
    Call<UpdateProductResponse> updateProduct(@Part MultipartBody.Part image,
                                              @Part("user_id") RequestBody userId, @Part("api_token") RequestBody apiToken,
                                              @Part("name") RequestBody password, @Part("price") RequestBody country_id,
                                              @Part("current_stock") RequestBody city_id, @Part("available_at") RequestBody startDate,
                                              @Part("new_available_qty") RequestBody availQty, @Part("status") RequestBody status,
                                              @Part("description") RequestBody desc, @Part("cat_id") RequestBody categoryId,
                                              @Part("sub_cat_id") RequestBody subCategoryId, @Part("location") RequestBody location,
                                              @Part("product_id") RequestBody productId);

    @GET("about-us")
    Call<AboutUsResponse> getAboutUs();

    @POST("product-rate")
    Call<ProductRateResponse> rateProduct(@Body ProductRateRequest productRateRequest);

}
