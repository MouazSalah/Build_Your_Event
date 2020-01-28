package com.buildyourevent.buildyourevent.model.auth.logout;

public class LogoutRequest
{
    int user_id;
    String api_token;

    public LogoutRequest() {
    }

    public LogoutRequest(int user_id, String api_token) {
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
