package com.buildyourevent.buildyourevent.ui.location;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by MahmoudAyman on 8/17/2019.
 **/
public abstract class PermissionManager {

    public static boolean isGranted(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(permission) == PERMISSION_GRANTED;
        } else
            return true;
    }

    public static Observable<Boolean> request(FragmentActivity context, String permission) {
        RxPermissions rxPermissions;
        rxPermissions = new RxPermissions(context);
        return rxPermissions.request(permission)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<Boolean> request(FragmentActivity context, String[] permissions) {
        RxPermissions rxPermissions;
        rxPermissions = new RxPermissions(context);
        return rxPermissions.request(permissions);
    }

    public static boolean hasImagePermission(FragmentActivity fragmentActivity) {
        if (isGranted(fragmentActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (isGranted(fragmentActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                return isGranted(fragmentActivity, Manifest.permission.CAMERA);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
