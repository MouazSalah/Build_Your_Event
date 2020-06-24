package com.buildyourevent.buildyourevent.user.home.subcategory;

import androidx.lifecycle.LiveData;
import com.buildyourevent.buildyourevent.model.data.product.ProductResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;
import com.buildyourevent.buildyourevent.user.home.BaseViewModel;

public class SubCategoryViewModel extends BaseViewModel
{
    SubCategoryRepository repository = new SubCategoryRepository();

    public LiveData<SubCategoryResponse> getAllSubCategories(int id)
    {
        return repository.getSubCategoryMutableLiveData(id);
    }

    public LiveData<ProductResponse> getAllProducts(String subCategoryId)
    {
        return repository.getProductsMutableLiveData(subCategoryId);
    }

    public LiveData<ProductResponse> getProducts()
    {
        return repository.getAllProductsMutableLiveData();
    }
}
