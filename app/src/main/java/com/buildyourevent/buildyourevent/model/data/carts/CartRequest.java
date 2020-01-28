package com.buildyourevent.buildyourevent.model.data.carts;

import com.google.gson.annotations.SerializedName;

public class CartRequest
{
    @SerializedName("user_id")
    int user_id;

    @SerializedName("api_token")
    String api_token;

    public CartRequest() {
    }

    public CartRequest(int user_id, String api_token) {
        this.user_id = user_id;
        this.api_token = api_token;
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
