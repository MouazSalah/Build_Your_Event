package com.buildyourevent.buildyourevent.model.data.productrate;

import com.google.gson.annotations.SerializedName;

public class ProductRateRequest
{
    @SerializedName("user_id")
    int user_id;

    @SerializedName("api_token")
    String api_token;

    @SerializedName("product_id")
    int productId;

    @SerializedName("rate")
    int rate;

    public ProductRateRequest() {
    }

    public ProductRateRequest(int user_id, String api_token, int productId, int rate) {
        this.user_id = user_id;
        this.api_token = api_token;
        this.productId = productId;
        this.rate = rate;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
