package com.buildyourevent.buildyourevent.model.notification;

public class NotificationItem
{
    String name, time;

    public NotificationItem() {
    }

    public NotificationItem(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
