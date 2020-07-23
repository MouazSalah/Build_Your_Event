package com.buildyourevent.buildyourevent.ui.location;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.buildyourevent.buildyourevent.model.constants.Codes;


public abstract class MovementManager
{
    //---------Fragments----------//

    public static void popAllFragments(Context context) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public static void replaceFragment(Context context, Fragment fragment, int containerResId, String backStackText) {
        try {
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(containerResId, fragment);
            if (!backStackText.equals("")) {
                fragmentTransaction.addToBackStack(backStackText);
            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void replaceFragment(Context context, Fragment fragment, int containerResId, String backStackText, Bundle bundle) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(containerResId, fragment);
        if (!backStackText.equals("")) {
            fragmentTransaction.addToBackStack(backStackText);
        }
        fragmentTransaction.commit();
    }

/*    public static void replaceFragment(Context context, Fragment fragment, String backStackText) {
        try {
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(CONTAINER_ID, fragment);
            if (!backStackText.equals("")) {
                fragmentTransaction.addToBackStack(backStackText);
            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            //Timber.e(e);
        }
    }

    public static void replaceFragment(Context context, Fragment fragment, String backStackText, Bundle bundle) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(CONTAINER_ID, fragment);
        if (!backStackText.equals("")) {
            fragmentTransaction.addToBackStack(backStackText);
        }
        fragmentTransaction.commit();
    }*/

    public static void openGPSSetting(Activity activity) {
        Intent callGPSSettingIntent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(callGPSSettingIntent, Codes.GPS_SETTINGS_REQ_CODE);
    }

}
