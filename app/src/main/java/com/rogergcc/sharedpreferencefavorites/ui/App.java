/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui;

import android.app.Application;
import android.content.res.Resources;

import com.rogergcc.sharedpreferencefavorites.ui.helpers.ConnectivityReceiver;
import com.rogergcc.sharedpreferencefavorites.ui.utils.AppLogger;


public class App extends Application {
    private static Resources resources;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        resources = getResources();
        //SharedPrefsManager.initialize(this);
        AppLogger.init();
//        HwAds.init(this);
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public static Resources getAppResources() {
        return resources;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}