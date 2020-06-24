package com.buildyourevent.buildyourevent.utils;

import android.app.Application;


public class MyApp extends Application
{
    private static MyApp context;

    public void onCreate()
    {
        super.onCreate();
        context = this;
    }

    public static MyApp getInstance() {
        return context;
    }
}