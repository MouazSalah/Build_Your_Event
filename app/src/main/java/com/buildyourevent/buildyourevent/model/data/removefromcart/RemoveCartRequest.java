package com.buildyourevent.buildyourevent.model.data.removefromcart;

import com.google.gson.annotations.SerializedName;

public class RemoveCartRequest
{
    @SerializedName("user_id")
    int user_id;

    @SerializedName("api_token")
    String api_token;

    @SerializedName("cart_id")
    int cartId;

    public RemoveCartRequest() {
    }

    public RemoveCartRequest(int user_id, String api_token, int cartId) {
        this.user_id = user_id;
        this.api_token = api_token;
        this.cartId = cartId;
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

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}
