package com.buildyourevent.buildyourevent.ui.CategoriesFragment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.banner.BannerData;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderImageAdapter extends SliderViewAdapter<SliderImageAdapter.SliderAdapterVH>
{
    private Context context;
    private int mCount;
    List<BannerData> bannerItemList= new ArrayList<>();

    public SliderImageAdapter(List<BannerData> bannerItemList, Context context)
    {
        this.bannerItemList = bannerItemList;
        this.context = context;
    }

    public void setCount(int count)
    {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent)
    {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position)
    {
        BannerData bannerItem = bannerItemList.get(position);
        Log.d(Codes.APP_TAGS, "banner ID " + bannerItem.getId());
        Log.d(Codes.APP_TAGS, "banner image " + bannerItem.getImage());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(Codes.APP_TAGS, "banner items counter : " + bannerItemList.size());
        Log.d(Codes.APP_TAGS, "banner items mCounter : " + mCount);



        for (int i = 0; i < bannerItemList.size(); i++)
        {
            viewHolder.textViewDescription.setTextSize(16);
            viewHolder.textViewDescription.setTextColor(Color.WHITE);
            viewHolder.imageGifContainer.setVisibility(View.GONE);
            Glide.with(viewHolder.itemView).load(bannerItemList.get(i).getImage()).fitCenter().into(viewHolder.imageViewBackground);
        }

        /*switch (position)
        {
            case 0:
              //  viewHolder.textViewDescription.setText("This is slider item " + position);
                viewHolder.textViewDescription.setTextSize(16);
                viewHolder.textViewDescription.setTextColor(Color.WHITE);
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView).load(R.drawable.watch0).fitCenter().into(viewHolder.imageViewBackground);
                break;
            case 1:
                //viewHolder.textViewDescription.setText("This is slider item " + position);
                viewHolder.textViewDescription.setTextSize(16);
                viewHolder.textViewDescription.setTextColor(Color.WHITE);
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView).load(R.drawable.summer1).fitCenter().into(viewHolder.imageViewBackground);
                break;
            case 2:
                //viewHolder.textViewDescription.setText("This is slider item " + position);
                viewHolder.textViewDescription.setTextSize(16);
                viewHolder.textViewDescription.setTextColor(Color.WHITE);
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.newbalance)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 4:
               // viewHolder.textViewDescription.setText("This is slider item " + position);
                viewHolder.textViewDescription.setTextSize(16);
                viewHolder.textViewDescription.setTextColor(Color.WHITE);
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.slide2)
                        .optionalFitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            default:
                viewHolder.textViewDescription.setTextSize(29);
                viewHolder.textViewDescription.setTextColor(Color.WHITE);
                viewHolder.imageGifContainer.setVisibility(View.VISIBLE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.puma_offer)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);

                break;

        }
        */
    }

    @Override
    public int getCount()
    {
        //slider view count could be dynamic size
        return mCount;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder
    {
        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView)
        {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
