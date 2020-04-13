package com.buildyourevent.buildyourevent.ui.CategoriesFragment;

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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.model.data.category.CategoryData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.ui.notification.NotificationFragment;
import com.buildyourevent.buildyourevent.ui.products.ProductsFragment;
import com.buildyourevent.buildyourevent.utils.MovementManager;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.bumptech.glide.Glide;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> 
{
    private Context mContext;
    private List<CategoryData> categoryList;

    public class MyViewHolder extends RecyclerView.ViewHolder 
    {
        public TextView name;
        public ImageView image;
        LinearLayout layout;

        public MyViewHolder(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.categoryname_textview);
            image = (ImageView) view.findViewById(R.id.category_imageview);
            layout = (LinearLayout) view.findViewById(R.id.categry_layout);
        }

        public void setAnimation()
        {
            Animation anim = AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.fade_transition_btt);
            itemView.startAnimation(anim);
        }
    }


    public CategoryAdapter(Context mContext, List<CategoryData> categoryList)
    {
        this.mContext = mContext;
        this.categoryList = categoryList;
        Log.d(Codes.APP_TAGS, "adapter categories: " + categoryList.size());
        Log.d(Codes.APP_TAGS, "adapter category name: " + categoryList.get(0).getCategoryName());
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_category, parent, false);
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.setAnimation();

        CategoryData categoryItem = categoryList.get(position);
        Log.d(Codes.APP_TAGS, "category name : " + categoryItem.getCategoryName());
        holder.name.setText(categoryItem.getCategoryName());

        Glide.with(mContext).load(categoryItem.getCategoryImage()).error(R.drawable.app_logo)
                .placeholder(R.drawable.app_logo).into(holder.image);
        holder.layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /*Intent intent = new Intent(mContext, ProductsActivity.class);
                intent.putExtra(Codes.CATEGORY_ID, categoryItem.getId());
                mContext.startActivity(intent);*/

                SharedPrefMethods prefMethods = new SharedPrefMethods(mContext);
                prefMethods.saveCategoryId(categoryItem.getId());

                MovementManager.replaceFragment(mContext, new ProductsFragment(), R.id.nav_host_fragment,"ProductsFragment");

                /*Fragment currentFragment = new ProductsFragment();
                FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, currentFragment);
                ft.commit();*/
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return categoryList.size();
    }
}