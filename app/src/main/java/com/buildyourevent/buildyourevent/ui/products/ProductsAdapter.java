package com.buildyourevent.buildyourevent.ui.products;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.product.ProductData;
import com.bumptech.glide.Glide;
import com.hzn.lib.EasyTransition;
import com.hzn.lib.EasyTransitionOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder>
{
    public Context mContext;
    public List<ProductData> productsList;


    public ProductsAdapter(Context mContext, List<ProductData> productsList)
    {
        this.mContext = mContext;
        this.productsList = productsList;
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
                Intent intent = new Intent(mContext.getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra(Codes.PRODUCT_ID, product.getProductId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // ready for transition options
                mContext.startActivity(intent);
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
