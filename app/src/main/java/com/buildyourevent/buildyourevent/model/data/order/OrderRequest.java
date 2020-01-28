package com.buildyourevent.buildyourevent.model.data.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderRequest implements Serializable
{
    @SerializedName("user_id")
    int user_id;

    @SerializedName("api_token")
    String api_token;

    @SerializedName("total_price")
    double totalPrice;

    public OrderRequest() {
    }

    public OrderRequest(int user_id, String api_token, double totalPrice) {
        this.user_id = user_id;
        this.api_token = api_token;
        this.totalPrice = totalPrice;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
