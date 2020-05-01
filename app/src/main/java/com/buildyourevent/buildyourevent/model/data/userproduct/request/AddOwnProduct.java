package com.buildyourevent.buildyourevent.model.data.userproduct.request;

public class AddOwnProduct {
    int user_id;
    String api_token;
    String name;
    String price;
    String current_stock;
    String available_at;
    String new_available_qty;
    String status;
    String description;
    int cat_id;
    int sub_cat_id;
    String location;

    public AddOwnProduct(int id, String token, String s, String toString, String string, String startDate, String endDate, String s1, String toString1, String string1, int i, int i1, String address) {
    }

    public AddOwnProduct(int user_id, String api_token, String name, String price, String current_stock,
                         String available_at, String new_available_qty, String status,
                         String description, int cat_id, int sub_cat_id, String location) {
        this.user_id = user_id;
        this.api_token = api_token;
        this.name = name;
        this.price = price;
        this.current_stock = current_stock;
        this.available_at = available_at;
        this.new_available_qty = new_available_qty;
        this.status = status;
        this.description = description;
        this.cat_id = cat_id;
        this.sub_cat_id = sub_cat_id;
        this.location = location;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrent_stock() {
        return current_stock;
    }

    public void setCurrent_stock(String current_stock) {
        this.current_stock = current_stock;
    }

    public String getAvailable_at() {
        return available_at;
    }

    public void setAvailable_at(String available_at) {
        this.available_at = available_at;
    }

    public String getNew_available_qty() {
        return new_available_qty;
    }

    public void setNew_available_qty(String new_available_qty) {
        this.new_available_qty = new_available_qty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(int sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}