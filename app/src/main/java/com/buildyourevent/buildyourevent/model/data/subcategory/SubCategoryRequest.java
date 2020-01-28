package com.buildyourevent.buildyourevent.model.data.subcategory;

public class SubCategoryRequest
{
    int categoryId;

    public SubCategoryRequest() {
    }

    public SubCategoryRequest(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
