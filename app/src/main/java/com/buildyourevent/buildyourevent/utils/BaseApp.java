package com.buildyourevent.buildyourevent.utils;

import android.app.Application;
import org.jetbrains.annotations.NotNull;

public class BaseApp extends Application
{
    private static BaseApp context;

    public void onCreate()
    {
        super.onCreate();
        context = this;
        // This line needs to be executed before any usage of EmojiTextView, EmojiEditText or EmojiButton.
    }


    public static BaseApp getInstance() {
        return context;
    }
}