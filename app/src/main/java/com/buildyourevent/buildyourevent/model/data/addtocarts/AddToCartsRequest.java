package com.buildyourevent.buildyourevent.model.data.addtocarts;

import com.google.gson.annotations.SerializedName;

public class AddToCartsRequest
{
    @SerializedName("lat")
    double lat;

    @SerializedName("lng")
    double lan;

    @SerializedName("user_id")
    int userId;

    @SerializedName("product_id")
    int productId;

    @SerializedName("quantity")
    int quantity;

    @SerializedName("days_num")
    int daysNum;

    @SerializedName("start_date")
    String startDay;

    @SerializedName("end_date")
    String endDay;

    @SerializedName("api_token")
    String apiToken;

    @SerializedName("address")
    String address;

    public AddToCartsRequest() {
    }

    public AddToCartsRequest(double lat, double lan, int userId, int productId, int quantity, int daysNum,
                             String startDay, String endDay, String apiToken, String address) {
        this.lat = lat;
        this.lan = lan;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.daysNum = daysNum;
        this.startDay = startDay;
        this.endDay = endDay;
        this.apiToken = apiToken;
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLan() {
        return lan;
    }

    public void setLan(double lan) {
        this.lan = lan;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDaysNum() {
        return daysNum;
    }

    public void setDaysNum(int daysNum) {
        this.daysNum = daysNum;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
