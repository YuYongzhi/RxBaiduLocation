package com.kevin.baidu.location;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.kevin.baidu.location.exception.GpsClosedException;
import com.kevin.baidu.location.exception.NoPermissionException;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * <p>
 * - Created by: yongzhi.
 * <br>
 * -       Date: 17-10-18.
 */

public class RxLocation {

    private static class RxLocationInner {
        private static RxLocation instance = new RxLocation();
    }

    public static RxLocation get() {
        return RxLocationInner.instance;
    }

    public Observable<Location> location(@NonNull final AppCompatActivity activity,
                                         long timeoutMillis) {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        Observable<Location> observable = Observable.just(timeoutMillis)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new RxPermissions(activity).ensure(permissions))
                .filter(new Predicate<Boolean>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            throw new NoPermissionException("缺少权限");
                        }
                        return true;
                    }
                })
                .filter(new Predicate<Boolean>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        if (!enabled) {
                            throw new GpsClosedException("请打开GPS开关");
                        }
                        return true;
                    }
                })
                .map(new Function<Boolean, Location>() {
                    @Override
                    public Location apply(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        return new Location();
                    }
                });

        return observable;
    }
}
