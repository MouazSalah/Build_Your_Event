package com.buildyourevent.buildyourevent.model.data.userproduct.request;

public class RemoveProductRequest
{
    String product_id;
    int user_id;
    String api_token;

    public RemoveProductRequest() {
    }

    public RemoveProductRequest(String product_id, int user_id, String api_token) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.api_token = api_token;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
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
