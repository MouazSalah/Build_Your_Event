package com.buildyourevent.buildyourevent.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public abstract class MovementManager {


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

}
