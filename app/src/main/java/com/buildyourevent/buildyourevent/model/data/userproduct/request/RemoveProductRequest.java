package com.buildyourevent.buildyourevent.model.data.userproduct.request;

import com.google.gson.annotations.SerializedName;

public class RemoveProductRequest
{
    @SerializedName("product_id")
    int product_id;

    @SerializedName("user_id")
    int user_id;

    @SerializedName("api_token")
    String api_token;

    public RemoveProductRequest() {
    }

    public RemoveProductRequest(int product_id, int user_id, String api_token) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.api_token = api_token;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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
}
