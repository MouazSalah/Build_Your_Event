package com.buildyourevent.buildyourevent.model.auth.login;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public LoginRequest() {
    }


    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
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


    @NonNull
    @Override
    public String toString() {
        return "LoginRequest{" +
                ",email= " + email + "\n" +
                ",password= " + password + "\n" +
                "}";
    }
}
