package com.buildyourevent.buildyourevent.ui.products;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.product.ProductData;
import com.buildyourevent.buildyourevent.ui.notification.NotificationFragment;
import com.buildyourevent.buildyourevent.utils.MovementManager;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder>
{
    public Context mContext;
    public List<ProductData> productsList;
    SharedPrefMethods prefMethods;


    public ProductsAdapter(Context mContext, List<ProductData> productsList)
    {
        this.mContext = mContext;
        this.productsList = productsList;
        prefMethods = new SharedPrefMethods(mContext);
    }

    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_products, parent, false);

        return new ProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.MyViewHolder holder, final int position)
    {
        holder.setAnimation();
        ProductData product = productsList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("" + product.getPrice());
        Glide.with(mContext).load(product.getImage()).into(holder.imgThumbnail);


        holder.layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               /* Intent intent = new Intent(mContext.getApplicationContext(), ProductDetailsActivity.class);
                prefMethods.saveProductId(product.getProductId());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // ready for transition options
                mContext.startActivity(intent);*/

                prefMethods.saveProductId(product.getProductId());
                MovementManager.replaceFragment(mContext, new ProductDetailsFragment(), R.id.nav_host_fragment,"ProductDetailsFragment");

               /* Fragment currentFragment = new ProductDetailsFragment();
                FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, currentFragment);
                ft.commit();*/
            }
        });
    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvName;
        public TextView tvPrice;
        public ImageView imgThumbnail;
        RelativeLayout layout;

        public MyViewHolder(View view)
        {
            super(view);
            tvName = (TextView) view.findViewById(R.id.itemproduct_name);
            tvPrice = (TextView) view.findViewById(R.id.itemproduct_price);
            imgThumbnail = (ImageView) view.findViewById(R.id.itemproduct_imageview);
            layout = (RelativeLayout) view.findViewById(R.id.rawproduct_layout);

        }
        public void setAnimation()
        {
            Animation anim = AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.zoom_in);
            itemView.startAnimation(anim);
        }
    }

}
