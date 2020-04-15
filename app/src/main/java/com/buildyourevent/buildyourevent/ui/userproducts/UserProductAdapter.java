package com.buildyourevent.buildyourevent.ui.userproducts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;

import com.buildyourevent.buildyourevent.model.data.userproduct.response.UserOwnProductData;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserProductAdapter extends RecyclerView.Adapter<UserProductAdapter.MyViewHolder>
{
    private Context mContext;
    private List<UserOwnProductData> productDataList;

    private UserProductAdapter.onCartItemListener listhener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        UserProductAdapter.onCartItemListener onCartItemListener;
        public TextView cardName, cardPrice;
        public ImageView cardImage;

        public MyViewHolder(View view)
        {
            super(view);
            cardName = (TextView) view.findViewById(R.id.userproduct_nametext);
            cardPrice = (TextView) view.findViewById(R.id.userproduct_pricetext);
            cardImage = (ImageView) view.findViewById(R.id.userproduct_imageview);

            onCartItemListener = listhener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            onCartItemListener.onItemCartClick(productDataList.get(getAdapterPosition()));
        }
    }

    public UserProductAdapter(Context mContext, List<UserOwnProductData> cartList, UserProductAdapter.onCartItemListener onCartItemListener)
    {
        this.mContext = mContext;
        this.productDataList = cartList;
        this.listhener = onCartItemListener;
        Log.d(Codes.APP_TAGS, "adapter carts size // " + productDataList.size());
    }

    @Override
    public UserProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_product_item, parent, false);
        return new UserProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserProductAdapter.MyViewHolder holder, final int position)
    {
        UserOwnProductData cardItem = productDataList.get(position);

        holder.cardName.setText("" + cardItem.getName());
        holder.cardPrice.setText("" + cardItem.getPrice());
        Glide.with(mContext).load(cardItem.getImage()).into(holder.cardImage);
    }

    @Override
    public int getItemCount()
    {
        return productDataList.size();
    }

    public interface onCartItemListener
    {
        void onItemCartClick(UserOwnProductData userOwnProductData);
    }
}
