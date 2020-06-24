package com.buildyourevent.buildyourevent.ui.user.subcategory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.ItemSubcategoryBinding;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import java.util.ArrayList;
import java.util.List;

public class SubDataAdapter extends RecyclerView.Adapter<SubDataAdapter.SubDataHolder>
{
    List<SubCategoryData> SubCategoryList = new ArrayList<>();
    public MutableLiveData<Object> mutableLiveData = new MutableLiveData<>();


    public SubDataAdapter() {
    }

    @NonNull
    @Override
    public SubDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemSubcategoryBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_item_filter, parent, false);
        return new SubDataHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubDataHolder holder, int position)
    {
        holder.binding.setModel(SubCategoryList.get(position));

      /*  holder.binding.textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClicked(SubCategoryList.get(position));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return SubCategoryList.size();
    }

    private SubCategoryData getCurrentItem(int pos) {
        return SubCategoryList.get(pos);
    }

    public void setSubCategoryList(List<SubCategoryData> models) {
        SubCategoryList = models;
        notifyDataSetChanged();
    }

    public class SubDataHolder extends RecyclerView.ViewHolder
    {
        private ItemSubcategoryBinding binding;

        public SubDataHolder(ItemSubcategoryBinding SubCategoryBinding)
        {
            super(SubCategoryBinding.getRoot());
            this.binding = SubCategoryBinding;

            binding.subcategoryitemLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.d(Codes.APP_TAGS, "item view clickedd // " + getAdapterPosition());
                    if (getCurrentItem(getAdapterPosition()).isSelect())
                    {
                        mutableLiveData.setValue(getCurrentItem(getAdapterPosition()));
                        binding.ivSubSelect.setImageResource(R.drawable.filter_background);
                    }
                    else
                    {
                        binding.ivSubSelect.setImageResource(R.drawable.icon_selected_foreground);
                        mutableLiveData.setValue(getCurrentItem(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
