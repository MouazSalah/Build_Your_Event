package com.buildyourevent.buildyourevent.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.data.product.ProductsData;
import com.buildyourevent.buildyourevent.ui.order.ProductDataFragment;
import com.buildyourevent.buildyourevent.utils.MovementManager;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> implements Filterable
{
    public Context mContext;
    public List<ProductsData> productsList = new ArrayList<>();
    SharedPrefMethods prefMethods;
    private List<ProductsData> filteredList;
    private List<ProductsData> oldListData;

    public void setOldListData(List<ProductsData> oldListData) {
        if (this.oldListData == null) {
            this.oldListData = new ArrayList<>();
        }
        this.oldListData = oldListData;
    }

    public ProductsAdapter(Context mContext)
    {
        this.mContext = mContext;
        prefMethods = new SharedPrefMethods(mContext);
    }

    @NotNull
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
        ProductsData product = productsList.get(position);
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
                MovementManager.replaceFragment(mContext, new ProductDataFragment(), R.id.nav_host_fragment,"ProductsDataFragment");

              //  Intent intent = new Intent(mContext.getApplicationContext(), ProductInfoActivity.class);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              //  mContext.startActivity(intent);


               // MovementManager.replaceFragment(mContext, new ProductDetailsFragment(), R.id.nav_host_fragment,"ProductDetailsFragment");

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


    @Override
    public Filter getFilter() {
        return new MyNamesFilter();
    }

    private class MyNamesFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String charString = constraint.toString();
            filteredList = new ArrayList<>();
            if (!charString.isEmpty()) {
                for (ProductsData row : oldListData) {
                    // set your conditions here.
                    if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                        filteredList.add(row);
                    }
                }
            } else {
                filteredList = new ArrayList<>();
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<ProductsData>) results.values;
            updateDataList(filteredList);
        }
    }

    public void updateDataList(List<ProductsData> list) {
        this.productsList = list;
        notifyDataSetChanged();
    }
}
