package com.buildyourevent.buildyourevent.ui.notification;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.notification.NotificationItem;
import com.buildyourevent.buildyourevent.model.constants.Codes;

import java.util.List;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>
{
    private Context mContext;
    private List<NotificationItem> notificationsList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;

        public MyViewHolder(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.norification_title);
        }
    }


    public NotificationAdapter(Context mContext, List<NotificationItem> categoryList)
    {
        this.mContext = mContext;
        this.notificationsList = categoryList;
        Log.d(Codes.APP_TAGS, "adapter notification: " + categoryList.size());
    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_notification, parent, false);
        return new NotificationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.MyViewHolder holder, final int position)
    {
        NotificationItem notificationItem = notificationsList.get(position);

        Log.d(Codes.APP_TAGS, "notification name : " + notificationItem.getName());
        holder.name.setText(notificationItem.getName());
    }

    @Override
    public int getItemCount()
    {
        return notificationsList.size();
    }

}

