package com.buildyourevent.buildyourevent.ui.cardactivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.carts.CartDataItem;
import com.buildyourevent.buildyourevent.ui.products.SubCategoryAdapter;
import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
{
    private Context mContext;
    private List<CartDataItem> cardsList;
    private onCartItemListener listhener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        onCartItemListener onCartItemListener;
        public TextView cardName, cardPrice;
        public ImageView cardImage;


        public MyViewHolder(View view)
        {
            super(view);
            cardName = (TextView) view.findViewById(R.id.itemcart_name);
            cardPrice = (TextView) view.findViewById(R.id.itemcart_price);
            cardImage = (ImageView) view.findViewById(R.id.itemcart_image);

            onCartItemListener = listhener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            onCartItemListener.onItemCartClick(getAdapterPosition());
        }
    }


    public CartAdapter(Context mContext, List<CartDataItem> cartList, onCartItemListener onCartItemListener)
    {
        this.mContext = mContext;
        this.cardsList = cartList;
        this.listhener = onCartItemListener;
        Log.d(Codes.APP_TAGS, "adapter carts size // " + cardsList.size());
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_cart, parent, false);
        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position)
    {
        CartDataItem cardItem = cardsList.get(position);

        holder.cardName.setText("" + cardItem.getProductName());
        holder.cardPrice.setText("" + cardItem.getProductPrice());
        Glide.with(mContext).load(cardItem.getProductImage()).into(holder.cardImage);
    }

    @Override
    public int getItemCount()
    {
        return cardsList.size();
    }

    public interface onCartItemListener
    {
        void onItemCartClick(int position);
    }
}