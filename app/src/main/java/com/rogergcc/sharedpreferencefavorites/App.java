package com.rogergcc.sharedpreferencefavorites;

import android.app.Application;
import android.content.res.Resources;

import com.rogergcc.sharedpreferencefavorites.helpers.ConnectivityReceiver;
import com.rogergcc.sharedpreferencefavorites.utils.AppLogger;


/**
 * Created by rogergcc on 2/03/2020.
 * Copyright Ⓒ 2020 . All rights reserved.
 */

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