package com.buildyourevent.buildyourevent.model.auth.change_password;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest
{
    @SerializedName("email")
    String email;

    @SerializedName("oldPassword")
    String oldPassword;

    @SerializedName("newPassword")
    String newPassowrd;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String email, String oldPassword, String newPassowrd) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassowrd = newPassowrd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassowrd() {
        return newPassowrd;
    }

    public void setNewPassowrd(String newPassowrd) {
        this.newPassowrd = newPassowrd;
    }
}
