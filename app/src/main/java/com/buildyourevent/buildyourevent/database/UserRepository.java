package com.buildyourevent.buildyourevent.database;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.buildyourevent.buildyourevent.model.data.aboutus.AboutUsResponse;
import com.buildyourevent.buildyourevent.model.data.addproduct.AddProductResponse;
import com.buildyourevent.buildyourevent.model.data.banner.BannerData;
import com.buildyourevent.buildyourevent.model.data.banner.BannerResponse;
import com.buildyourevent.buildyourevent.model.data.category.CategoryRequest;
import com.buildyourevent.buildyourevent.model.data.category.CategoryResponse;
import com.buildyourevent.buildyourevent.model.data.product.ProductsResponse;
import com.buildyourevent.buildyourevent.model.data.productdetails.ProductDetailsResponse;
import com.buildyourevent.buildyourevent.model.data.productrate.ProductRateRequest;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartResponse;
import com.buildyourevent.buildyourevent.model.data.addtocarts.AddToCartsRequest;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.order.OrderRequest;
import com.buildyourevent.buildyourevent.model.data.order.OrderResponse;
import com.buildyourevent.buildyourevent.model.data.productrate.ProductRateResponse;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartRequest;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;
import com.buildyourevent.buildyourevent.model.data.updateproduct.UpdateProductResponse;
import com.buildyourevent.buildyourevent.model.data.userproduct.request.RemoveProductRequest;
import com.buildyourevent.buildyourevent.model.data.userproduct.response.RemoveProductResponse;
import com.buildyourevent.buildyourevent.model.data.userproduct.response.UserOwnProductResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.content.ContentValues.TAG;

public class UserRepository
{
    InterfaceApi interfaceApi;

    private MutableLiveData<CategoryResponse> categoryLiveData = new MutableLiveData<>();

    private MutableLiveData<SubCategoryResponse> subCategoryLiveData = new MutableLiveData<>();

    private MutableLiveData<List<BannerData>> bannersMutableLiveData = new MutableLiveData<>();
    private ArrayList<BannerData> bannersList = new ArrayList<>();

    private MutableLiveData<ProductsResponse> allProductsLiveData = new MutableLiveData<>();

    private MutableLiveData<CartResponse> cartsMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<AddToCartResponse> addToCartsMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<RemoveCartResponse> removeFromCartsMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<OrderResponse> confirmOrderMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<ProductDetailsResponse> detailsLiveData = new MutableLiveData<>();

    private MutableLiveData<UserOwnProductResponse> userOwnProductsMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<AddProductResponse> addNewProductsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UpdateProductResponse> updateProductMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<RemoveProductResponse> removeProductsMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<AboutUsResponse> aboutUsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ProductRateResponse> productRateMutableLiveData = new MutableLiveData<>();

    public UserRepository()
    {
        interfaceApi = RetrofitClient.getApiClient(Codes.AUTH_BASE_URL).create(InterfaceApi.class);
        categoryLiveData = new MutableLiveData<>();
        bannersMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<BannerData>> getBannersMutableLiveData()
    {
        Call<BannerResponse> responseCall = interfaceApi.getAllBanners();
        responseCall.enqueue(new Callback<BannerResponse>()
        {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "banner not success" + response.message());
                }
                else
                {
                    BannerResponse bannerResponse = response.body();
                    if (bannerResponse != null && bannerResponse.getData() != null)
                    {
                        bannersList = (ArrayList<BannerData>) bannerResponse.getData();
                        bannersMutableLiveData.setValue(bannersList);
                    }
                }
            }
            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "banner failed" + t.getMessage());
            }
        });

        return bannersMutableLiveData;
    }

    public MutableLiveData<CategoryResponse> getCategoryMutableLiveData()
    {

        Call<CategoryResponse> responseCall = interfaceApi.getAllCategories();
        responseCall.enqueue(new Callback<CategoryResponse>()
        {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response)
            {
                Timber.tag("ana").e(response.raw().toString());
                if (response.isSuccessful())
                {
                    categoryLiveData.setValue(response.body());
                }
                else
                {

                }
            }
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "category failed" + t.getMessage());
            }
        });

        return categoryLiveData;
    }

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

    public MutableLiveData<ProductsResponse> getSelectedProducts(String subCategoryId)
    {
        Call<ProductsResponse> responseCall = interfaceApi.getProducts(subCategoryId);
        responseCall.enqueue(new Callback<ProductsResponse>()
        {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response)
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
            public void onFailure(Call<ProductsResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "products failed" + t.getMessage());
            }
        });

        return allProductsLiveData;
    }

    public MutableLiveData<ProductsResponse> getAllProducts()
    {
        Call<ProductsResponse> responseCall = interfaceApi.getAllProducts(Codes.CATEGORY_ID);
        responseCall.enqueue(new Callback<ProductsResponse>()
        {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response)
            {
                ProductsResponse productResponse = response.body();
                allProductsLiveData.setValue(productResponse);
            }
            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "products failed" + t.getMessage());
            }
        });

        return allProductsLiveData;
    }

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
                    Log.e(TAG, response.body().toString() );
                    detailsLiveData.setValue(response.body());
                }
                else
                {
                    Log.d(Codes.APP_TAGS, "product details not success" + response.message());
                }
            }
            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "product details failed" + t.getMessage());
            }
        });
        return detailsLiveData;
    }


    public MutableLiveData<CartResponse> getCartssMutableLiveData(int userId, String apiToken)
    {
        Call<CartResponse> responseCall = interfaceApi.getAllCarts(userId, apiToken);
        responseCall.enqueue(new Callback<CartResponse>()
        {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response)
            {
               // cartsMutableLiveData.setValue(response.body());

               // Log.d(Codes.APP_TAGS, "carts  success" + response.message());
                if (!response.isSuccessful())
                {
                   Log.d(Codes.APP_TAGS, "carts not success" + response.message());
                    // cartsMutableLiveData.setValue("error");
                    cartsMutableLiveData.setValue(response.body());
                }
                else
                {
                    cartsMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<CartResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "carts failed" + t.getMessage());
            }
        });

        return cartsMutableLiveData;  //this is wrong
    }

    public MutableLiveData<AddToCartResponse> getAddToCartsMutableLiveData(AddToCartsRequest addToCartsRequest)
    {
        Call<AddToCartResponse> responseCall = interfaceApi.addToCart(addToCartsRequest);
        responseCall.enqueue(new Callback<AddToCartResponse>()
        {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "add to carts not success" + response.message());
                }
                else
                {
                    Log.d(Codes.APP_TAGS, "add to carts success " + response.body().toString());
                    AddToCartResponse addToCartResponse = response.body();
                    addToCartsMutableLiveData.setValue(addToCartResponse);
                }
            }
            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "add to carts failed" + t.getMessage());
            }
        });

        return addToCartsMutableLiveData;
    }


    public MutableLiveData<RemoveCartResponse> getRemoveCartMutableLiveData(RemoveCartRequest removeCartRequest)
    {
        Call<RemoveCartResponse> responseCall = interfaceApi.removeFromCart(removeCartRequest);
        responseCall.enqueue(new Callback<RemoveCartResponse>()
        {
            @Override
            public void onResponse(Call<RemoveCartResponse> call, Response<RemoveCartResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "add to carts not success" + response.message());
                }
                else
                {
                    RemoveCartResponse removeCartResponse = response.body();
                    removeFromCartsMutableLiveData.setValue(removeCartResponse);
                }
            }
            @Override
            public void onFailure(Call<RemoveCartResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "add to carts failed" + t.getMessage());
            }
        });

        return removeFromCartsMutableLiveData;
    }


    public MutableLiveData<OrderResponse> getConfirmOrderMutableLiveData(OrderRequest orderRequest)
    {
        Call<OrderResponse> responseCall = interfaceApi.confirmOrder(orderRequest);
        responseCall.enqueue(new Callback<OrderResponse>()
        {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(Codes.APP_TAGS, "add to carts not success" + response.message());
                }
                else
                {
                    OrderResponse confrimOrderResponse = response.body();
                    confirmOrderMutableLiveData.setValue(confrimOrderResponse);
                }
            }
            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t)
            {
                Log.d(Codes.APP_TAGS, "add to carts failed" + t.getMessage());
            }
        });

        return confirmOrderMutableLiveData;
    }


    public MutableLiveData<UserOwnProductResponse> getShowAllUserProductMutapleLiveData(int userId, String apiToekn)
    {
        Call<UserOwnProductResponse> responseCall = interfaceApi.showOwnProducts(userId, apiToekn);
        responseCall.enqueue(new Callback<UserOwnProductResponse>()
        {
            @Override
            public void onResponse(Call<UserOwnProductResponse> call, Response<UserOwnProductResponse> response)
            {
                Log.d("eventsontime", response.message());
                if (!response.isSuccessful())
                {

                }
                else
                {
                    UserOwnProductResponse userOwnProductResponse = response.body();
                    userOwnProductsMutableLiveData.setValue(userOwnProductResponse);
                }
            }
            @Override
            public void onFailure(Call<UserOwnProductResponse> call, Throwable t)
            {
            }
        });

        return userOwnProductsMutableLiveData;
    }


    public MutableLiveData<AddProductResponse> getAddNewProductsMutableLiveData(MultipartBody.Part pic, RequestBody string1,
                                    RequestBody string2, RequestBody string3,
                                    RequestBody string4, RequestBody string5,
                                    RequestBody string6, RequestBody string7,
                                    RequestBody string8, RequestBody string9,
                                    RequestBody string10, RequestBody string11,
                                    RequestBody string12)
    {
        Call<AddProductResponse> responseCall = interfaceApi.addOwnProducts(pic, string1,string2, string3, string4, string5, string6,
                                                                            string7, string8, string9, string10, string11, string12);
        responseCall.enqueue(new Callback<AddProductResponse>()
        {
            @Override
            public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response)
            {
                Log.d(Codes.APP_TAGS, "add product result // " +  response.message());
                Log.d(Codes.APP_TAGS, "add product result // " +  response.message());

                if (!response.isSuccessful())
                {

                }
                else
                {
                    AddProductResponse userOwnProductResponse = response.body();
                    addNewProductsMutableLiveData.setValue(userOwnProductResponse);
                }
            }
            @Override
            public void onFailure(Call<AddProductResponse> call, Throwable t)
            {
            }
        });

        return addNewProductsMutableLiveData;
    }



    public MutableLiveData<UpdateProductResponse> getUpdateProductsMutableLiveData(MultipartBody.Part pic, RequestBody string1,
                                                                                   RequestBody string2, RequestBody string3,
                                                                                   RequestBody string4, RequestBody string5,
                                                                                   RequestBody string6, RequestBody string7,
                                                                                   RequestBody string8, RequestBody string9,
                                                                                   RequestBody string10, RequestBody string11,
                                                                                   RequestBody string12, RequestBody string13)
    {
        Call<UpdateProductResponse> responseCall = interfaceApi.updateProduct(pic, string1,string2, string3, string4, string5, string6,
                                                                       string7, string8, string9, string10, string11, string12, string13);
        responseCall.enqueue(new Callback<UpdateProductResponse>()
        {
            @Override
            public void onResponse(Call<UpdateProductResponse> call, Response<UpdateProductResponse> response)
            {
                Log.d(Codes.APP_TAGS, "update product // " + response.message());
                if (!response.isSuccessful())
                {

                }
                else
                {
                    UpdateProductResponse userOwnProductResponse = response.body();
                    updateProductMutableLiveData.setValue(userOwnProductResponse);
                }
            }
            @Override
            public void onFailure(Call<UpdateProductResponse> call, Throwable t)
            {
            }
        });

        return updateProductMutableLiveData;
    }

    public MutableLiveData<ProductRateResponse> getProductRateMutableLiveData(ProductRateRequest productRateRequest)
    {
        Call<ProductRateResponse> responseCall = interfaceApi.rateProduct(productRateRequest);
        responseCall.enqueue(new Callback<ProductRateResponse>()
        {
            @Override
            public void onResponse(Call<ProductRateResponse> call, Response<ProductRateResponse> response)
            {
                Log.d("eventsontime", response.message());
                if (!response.isSuccessful())
                {

                }
                else
                {
                    ProductRateResponse userOwnProductResponse = response.body();
                    productRateMutableLiveData.setValue(userOwnProductResponse);
                }
            }
            @Override
            public void onFailure(Call<ProductRateResponse> call, Throwable t)
            {
            }
        });

        return productRateMutableLiveData;
    }


    public MutableLiveData<RemoveProductResponse> getRemoveProductsMutableLiveData(RemoveProductRequest removeProductRequest)
    {
        Call<RemoveProductResponse> responseCall = interfaceApi.removeProduct(removeProductRequest);
        responseCall.enqueue(new Callback<RemoveProductResponse>()
        {
            @Override
            public void onResponse(Call<RemoveProductResponse> call, Response<RemoveProductResponse> response)
            {
                Log.d("eventsontime", response.message());
                if (!response.isSuccessful())
                {

                }
                else
                {
                    RemoveProductResponse userOwnProductResponse = response.body();
                    removeProductsMutableLiveData.setValue(userOwnProductResponse);
                }
            }
            @Override
            public void onFailure(Call<RemoveProductResponse> call, Throwable t)
            {
            }
        });

        return removeProductsMutableLiveData;
    }


    public MutableLiveData<AboutUsResponse> getAboutUsMutableLiveData()
    {
        Call<AboutUsResponse> responseCall = interfaceApi.getAboutUs();
        responseCall.enqueue(new Callback<AboutUsResponse>()
        {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response)
            {
                Log.d("eventsontime", response.message());
                if (response.isSuccessful())
                {
                    AboutUsResponse userOwnProductResponse = response.body();
                    aboutUsMutableLiveData.setValue(userOwnProductResponse);
                }
            }
            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t)
            {
            }
        });

        return aboutUsMutableLiveData;
    }
}
