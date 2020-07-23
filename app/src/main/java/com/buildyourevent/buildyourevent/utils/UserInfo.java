package com.buildyourevent.buildyourevent.utils;

import com.buildyourevent.buildyourevent.model.auth.login.UserData;

import java.io.Serializable;

public class UserInfo implements Serializable
{
    private static volatile UserInfo sSoleInstance;
    private static volatile UserData userData;

    //private constructor.
    private UserInfo() {

        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static UserInfo getInstance()
    {
        if (sSoleInstance == null)
        {
            //if there is no instance available... create new one
            synchronized (UserInfo.class)
            {
                if (sSoleInstance == null)
                    sSoleInstance = new UserInfo();
                    userData = PrefMethods.getUserData() ;
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected UserInfo readResolve() {
        return getInstance();
    }
}
