package com.buildyourevent.buildyourevent.model.auth.register;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest
{
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("country_id")
    private int country_id;

    @SerializedName("city_id")
    private int city_id;


    @SerializedName("image")
    private Uri image;

    public RegisterRequest(String name, String email, String password, String mobile, int countryId, int cityId, Bitmap bitmapPhoto) {
    }

    public RegisterRequest(String name, String email, String password, String mobile, int country_id, int city_id, Uri image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.country_id = country_id;
        this.city_id = city_id;
        this.image = image;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }
}
