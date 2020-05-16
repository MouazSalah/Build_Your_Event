package com.buildyourevent.buildyourevent.ui.products;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder>
{
    private Context mContext;
    private static List<SubCategoryData> subCategoryList;

    private onSubCategoryListener onSubCategoryListener;
    private SharedPrefMethods prefMethods;
    public MutableLiveData<Object> mutableLiveData = new MutableLiveData<>();

    public SubCategoryAdapter(Context mContext, List<SubCategoryData> subCategoryList, onSubCategoryListener onSubCategoryListener) {
        this.mContext = mContext;
        prefMethods = new SharedPrefMethods(mContext);
        this.subCategoryList = subCategoryList;
        this.onSubCategoryListener = onSubCategoryListener;
    }

    @NotNull
    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_item_filter, parent, false);

        return new SubCategoryAdapter.MyViewHolder(itemView, onSubCategoryListener);
    }

    @Override
    public void onBindViewHolder(final SubCategoryAdapter.MyViewHolder holder, final int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


      if (prefMethods.isSmall())
        {
            Log.e(TAG, "is small");
            params.height = (int) mContext.getResources().getDimension(R.dimen.item_small_size_height);
            params.width = (int) mContext.getResources().getDimension(R.dimen.item_small_size_width);
            params.setMarginStart(20);
            params.setMarginEnd(20);
            params.topMargin = 20;

        }
      else
        {
            Log.e(TAG, "la");
            params.height = (int) mContext.getResources().getDimension(R.dimen.item_normal_size_height);
            params.width = (int) mContext.getResources().getDimension(R.dimen.item_normal_size_width);
            params.setMarginStart(20);
            params.setMarginEnd(20);
            params.topMargin = 20;
        }
        holder.setAnimation();
        holder.setData(getCurrentItem(position));
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvName;
        public ImageView itemImage, selectImage;
        onSubCategoryListener subCategoryListener;

        public MyViewHolder(View view, onSubCategoryListener listener)
        {
            super(view);
            tvName = (TextView) view.findViewById(R.id.itemfilter_textview);
            itemImage = (ImageView) view.findViewById(R.id.itemfilter_imageview);
            selectImage = view.findViewById(R.id.itemfilter_selected_imageview);

            subCategoryListener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Log.d(Codes.APP_TAGS, "item view clickedd // " + getAdapterPosition());
                    if (getCurrentItem(getAdapterPosition()).isSelect())
                    {
                        getCurrentItem(getAdapterPosition()).setSelect(false);
                        itemView.setSelected(false);
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
                    }
                }
            });
        }

        public void setAnimation() {
            Animation anim = AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.zoom_in);
            itemView.startAnimation(anim);
        }

        @Override
        public void onClick(View view) {
            subCategoryListener.onSubCategoryClick(getAdapterPosition(), getCurrentItem(getAdapterPosition()));
        }

        public void setData(SubCategoryData item)
        {
            tvName.setText(item.getSubcategoryName());
            // loading album cover using Glide library
            if (item.getSubcategoryName().equals("ALL"))
            {
                Glide.with(mContext).load(item.getSubcategoryImage()).error(R.drawable.all_image)
                        .placeholder(R.drawable.all_image).into(itemImage);
            }
            else
            {
                Glide.with(mContext).load(item.getSubcategoryImage()).error(R.drawable.app_logo)
                        .placeholder(R.drawable.app_logo).into(itemImage);
            }
        }
    }

    private SubCategoryData getCurrentItem(int pos) {
        return subCategoryList.get(pos);
    }

    public interface onSubCategoryListener {
        void onSubCategoryClick(int position, SubCategoryData subCategoryData);

        void onSelected(int itemId);

        void onUnSelected(int itemId);

    }
}

