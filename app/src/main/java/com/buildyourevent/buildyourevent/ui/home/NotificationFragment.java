package com.buildyourevent.buildyourevent.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.notification.NotificationItem;
import com.buildyourevent.buildyourevent.ui.Adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NotificationFragment extends Fragment
{
    @BindView(R.id.notification_recyclerview) RecyclerView notifcationRecyclerView;

    private List<NotificationItem> notificationList;

    private Integer image[] = {R.drawable.watch0, R.drawable.summer1, R.drawable.summer2, R.drawable.winter, R.drawable.summer1
            , R.drawable.watch0, R.drawable.summer1, R.drawable.summer2, R.drawable.winter, R.drawable.summer1};
    private String title[] = {"T shirt", "suit shirt", "Pantalon", "Chemise", "Laptop", "T shirt", "suit shirt", "Pantalon", "Chemise", "Laptop"};
    private double price[] = {5, 7, 6, 15, 5, 7, 6, 15, 21, 5};


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_notification, container, false);

        notifcationRecyclerView = root.findViewById(R.id.notification_recyclerview);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 1);
        notifcationRecyclerView.setLayoutManager(manager);
        notifcationRecyclerView.setItemAnimator(new DefaultItemAnimator());

        notificationList = new ArrayList<>();

        for (int i = 0; i < 7; i++)
        {
            NotificationItem notificationItem = new NotificationItem("you ave a new notification for your account", "15 minute ago");
            notificationList.add(notificationItem);
        }

        NotificationAdapter notificationAdapter = new NotificationAdapter(getActivity(), notificationList);
        notifcationRecyclerView.setAdapter(notificationAdapter);

        return root;
    }
}