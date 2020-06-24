package com.buildyourevent.buildyourevent.user.home.subcategory;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.user.home.BaseViewModel;
import com.bumptech.glide.Glide;

public class ItemSubCatViewModel extends BaseViewModel
{
    public SubCategoryData subCategoryData;
    public ObservableBoolean observableBoolean = new ObservableBoolean();

    public ItemSubCatViewModel(SubCategoryData item)
    {
        this.subCategoryData = item;
    }

    public void onFilterButtonClicked()
    {
        setValue(subCategoryData);

        /*if (subCategoryData.isSelect())
        {
            getCurrentItem(getAdapterPosition()).setSelect(false);
            subCategoryData.setSelected(false);
            subCategoryListener.onUnSelected(getCurrentItem(getAdapterPosition()).getId());
            mutableLiveData.setValue(getCurrentItem(getAdapterPosition()));
            selectImage.setImageResource(R.drawable.filter_background);
        }
        else
        {
            itemView.setSelected(true);
            getCurrentItem(getAdapterPosition()).setSelect(true);
            subCategoryListener.onSelected(getCurrentItem(getAdapterPosition()).getId());   //!!!!!!
            selectImage.setImageResource(R.drawable.icon_selected_foreground);
            mutableLiveData.setValue(getCurrentItem(getAdapterPosition()));
        }*/
    }

    @BindingAdapter({"subImage"})
    public static void subCategoryImage(ImageView view, String image)
    {
        Glide.with(view.getContext()).load(image).into(view);
    }

    @BindingAdapter({"filterImage"})
    public static void filterImage(ImageView view, String image)
    {
        Glide.with(view.getContext()).load(image).into(view);
    }


}
