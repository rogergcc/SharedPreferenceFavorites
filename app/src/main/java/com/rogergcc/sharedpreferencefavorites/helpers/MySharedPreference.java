/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MySharedPreference {
    private SharedPreferences prefs;
    private Context context;

    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();
    public MySharedPreference(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
    }


    public void saveFavoritesMarkers(String product){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(Constants.FAVORITE_ID, product);
        edits.apply();
    }
    public String retrieveFavorites(){
        return prefs.getString(Constants.FAVORITE_ID, null);
    }

    public void addFavoriteCount(int productCount){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putInt(Constants.FAVORITE_COUNT, productCount);
        edits.apply();
    }


    public int retrieveFavoriteCount(){
        return prefs.getInt(Constants.FAVORITE_COUNT, 0);
    }
    public void deleteAllFavoriteCount( ){
        //prefs.edit().clear().apply();
        prefs.edit().remove(Constants.FAVORITE_COUNT).apply();
    }

    public void deleteAllFavorites( ){
        //prefs.edit().clear().apply();
        prefs.edit().remove(Constants.FAVORITE_ID).apply();
    }



}
