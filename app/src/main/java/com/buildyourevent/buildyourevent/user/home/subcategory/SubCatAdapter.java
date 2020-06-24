package com.buildyourevent.buildyourevent.user.home.subcategory;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.RawSubcategoryBinding;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;

import java.util.List;

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.SubCategoryViewHolder>
{
    private List<SubCategoryData> subCategoryList;
    public MutableLiveData mutableLiveData;

    public SubCatAdapter()
    {
        mutableLiveData = new MutableLiveData();
    }

    @NonNull
    @Override
    public SubCatAdapter.SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        RawSubcategoryBinding rawSubcategoryBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.raw_subcategory, viewGroup, false);
        return new SubCatAdapter.SubCategoryViewHolder(rawSubcategoryBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull SubCatAdapter.SubCategoryViewHolder employeeViewHolder, int i)
    {
        SubCategoryData messagesData = subCategoryList.get(i);

        ItemSubCatViewModel itemMediaViewModel = new ItemSubCatViewModel(messagesData);
        itemMediaViewModel.subCategoryData = messagesData;
        employeeViewHolder.rawItemFilterBinding.setModel(itemMediaViewModel);

    }

    @Override
    public int getItemCount()
    {
        if (subCategoryList != null)
        {
            return subCategoryList.size();
        }
        else
        {
            return 0;
        }
    }

    public void setSubCategoryList(List<SubCategoryData> subCategoryList)
    {
        this.subCategoryList = subCategoryList;
        notifyDataSetChanged();
    }

    class SubCategoryViewHolder extends RecyclerView.ViewHolder
    {
        private RawSubcategoryBinding rawItemFilterBinding;

        public SubCategoryViewHolder(@NonNull RawSubcategoryBinding rawMediaBinding)
        {
            super(rawMediaBinding.getRoot());
            this.rawItemFilterBinding = rawMediaBinding;
        }
    }
}

