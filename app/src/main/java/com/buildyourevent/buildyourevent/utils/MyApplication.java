package com.buildyourevent.buildyourevent.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.buildyourevent.buildyourevent.model.constants.Codes;

import java.io.Serializable;


public class MyApplication extends Application implements Serializable
{
    private static volatile MyApplication sSoleInstance;

    //private constructor.
    public MyApplication(){
        //Prevent form the reflection api.
        if (sSoleInstance != null){
           // throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static MyApplication getInstance()
    {
        if (sSoleInstance == null)
        {
            //if there is no instance available... create new one
            synchronized (MyApplication.class)
            {
                if (sSoleInstance == null) sSoleInstance = new MyApplication();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected MyApplication readResolve() {
        return getInstance();
    }

}