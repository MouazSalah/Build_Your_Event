package com.buildyourevent.buildyourevent.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.google.gson.Gson;

public class SharedPrefMethods
{
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SharedPrefMethods(Context con)
    {
        this.context = con;
        pref = con.getSharedPreferences(Codes.SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveUserLanguage(String lang)
    {
        editor.putString(Codes.SHARED_PREF_LANGUAGE, lang);
        editor.commit();
    }

    public String getUserLanguage()
    {
        return pref.getString(Codes.SHARED_PREF_LANGUAGE, null);
    }

    public void deleteUserLanguage()
    {
        editor.remove(Codes.SHARED_PREF_LANGUAGE);
        editor.apply();
    }

    public void SaveUserData(UserData userData)
    {
        Gson gson = new Gson();
        String json = gson.toJson(userData); // myObject - instance of MyObject
        editor.putString(Codes.SHARED_PREF_USER_DATA, json);
        editor.apply();
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
        editor.apply();
    }

    public void saveSubCategoryData(SubCategoryData subCategoryData)
    {
        Gson gson = new Gson();
        String json = gson.toJson(subCategoryData); // myObject - instance of MyObject
        editor.putString(Codes.SHARED_PREF_SUBCATEGORY_DATA, json);
        editor.apply();
    }

    public SubCategoryData getSubCategoryData()
    {
        SubCategoryData subCategoryData = new SubCategoryData();

        Gson gson = new Gson();
        String json = pref.getString(Codes.SHARED_PREF_SUBCATEGORY_DATA, null);
        subCategoryData = gson.fromJson(json, SubCategoryData.class);

        return subCategoryData;
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
}
