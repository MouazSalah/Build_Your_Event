package com.buildyourevent.buildyourevent.model.data.category;

import com.google.gson.annotations.SerializedName;

public class CategoryRequest {
    @SerializedName("type")
    double type;

    public CategoryRequest(double type) {
        this.type = type;
    }

    public double getType() {
        return type;
    }

    public void setType(double type) {
        this.type = type;
    }
}
