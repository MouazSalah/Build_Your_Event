package com.buildyourevent.buildyourevent.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefMethods implements Serializable
{
    private static volatile PrefMethods sSoleInstance;
    static SharedPreferences pref;
    static SharedPreferences.Editor editor;

    //private constructor.
    public PrefMethods(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static PrefMethods getInstance()
    {
        if (sSoleInstance == null)
        {
            //if there is no instance available... create new one
            synchronized (PrefMethods.class)
            {
                pref = MyApp.getInstance().getSharedPreferences(Codes.SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
                editor = pref.edit();
                if (sSoleInstance == null) sSoleInstance = new PrefMethods();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected PrefMethods readResolve() {
        return getInstance();
    }

    public void saveUserLanguage(String lang)
    {
        editor.putString(Codes.SHARED_PREF_LANGUAGE, lang);
        editor.commit();
        Log.d(Codes.APP_TAGS, "shared language: " + lang);
    }

    public String getUserLanguage()
    {
        return pref.getString(Codes.SHARED_PREF_LANGUAGE, null);
    }

    public void deleteUserLanguage()
    {
        editor.remove(Codes.SHARED_PREF_LANGUAGE);
        editor.commit();
    }

    public void SaveUserData(UserData userData)
    {
        Gson gson = new Gson();
        String json = gson.toJson(userData); // myObject - instance of MyObject
        editor.putString(Codes.SHARED_PREF_USER_DATA, json);
        editor.commit();
    }

    public UserData getUserData()
    {
        UserData userData = new UserData();

        Gson gson = new Gson();
        String json = pref.getString(Codes.SHARED_PREF_USER_DATA, null);
        userData = gson.fromJson(json, UserData.class);

        return userData;
    }

    public void deleteUserData()
    {
        editor.remove(Codes.SHARED_PREF_USER_DATA);
        editor.commit();
    }

    public void saveSubCategoryData(SubCategoryData subCategoryData)
    {
        Gson gson = new Gson();
        String json = gson.toJson(subCategoryData); // myObject - instance of MyObject
        editor.putString(Codes.SHARED_PREF_SUBCATEGORY_DATA, json);
        editor.commit();
    }

    public SubCategoryData getSubCategoryData()
    {
        SubCategoryData subCategoryData = new SubCategoryData();

        Gson gson = new Gson();
        String json = pref.getString(Codes.SHARED_PREF_SUBCATEGORY_DATA, null);
        subCategoryData = gson.fromJson(json, SubCategoryData.class);

        return subCategoryData;
    }

    public void saveCategoryId(int id)
    {
        editor.putInt(Codes.CATEGORY_ID, id);
        editor.commit();
    }

    public int getCategoryId()
    {
        return pref.getInt(Codes.CATEGORY_ID, 1);
    }


    public void saveCountryId(int id)
    {
        editor.putInt(Codes.COUNTRY_ID, id);
        editor.commit();
    }

    public int getCountryId()
    {
        return pref.getInt(Codes.COUNTRY_ID, 1);
    }

    public void saveProductId(int id)
    {
        editor.putInt(Codes.PRODUCT_ID, id);
        editor.commit();
    }

    public int getProductId()
    {
        return pref.getInt(Codes.PRODUCT_ID, 1);
    }



    public void setSmall(boolean isSmall)
    {
        editor.putBoolean(Codes.PREF_SMALL, isSmall);
        editor.commit();
    }

    public boolean isSmall()
    {
        return pref.getBoolean(Codes.PREF_SMALL, true);
    }


    public void setSelected(boolean isSelected)
    {
        editor.putBoolean(Codes.SHARED_PREF_IS_SELECTED, isSelected);
        editor.commit();
    }

    public boolean isSelected()
    {
        return pref.getBoolean("s", false);
    }


    public  void saveUserCandidates(List<Double> list)
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(Codes.SHARED_PREF_USER_LATLNG, json);
        editor.commit();
    }

    public List<Double> getUserCandidates()
    {
        Gson gson = new Gson();
        List<Double> productFromShared = new ArrayList<>();
        String jsonPreferences = pref.getString(Codes.SHARED_PREF_USER_LATLNG, "");

        Type type = new TypeToken<List<Double>>() {}.getType();

        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }


    public void saveCartsCount(int count)
    {
        editor.putInt(Codes.CARTS_COUNT, count);
        editor.commit();
    }

    public int getCartsCount()
    {
        return pref.getInt(Codes.CARTS_COUNT, 1);
    }

}

