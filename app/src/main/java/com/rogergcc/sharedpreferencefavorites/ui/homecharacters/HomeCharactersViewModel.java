/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui.homecharacters;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rogergcc.sharedpreferencefavorites.model.RickMortyResponse;
import com.rogergcc.sharedpreferencefavorites.remote.CommonApiUrl;
import com.rogergcc.sharedpreferencefavorites.remote.IApiService;
import com.rogergcc.sharedpreferencefavorites.ui.utils.AppLogger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCharactersViewModel extends ViewModel {
    private HomeCharactersRepository homeCharactersRepository;
    MutableLiveData<RickMortyResponse> datosmutable;
    private IApiService apiService;
    public HomeCharactersViewModel () {
        homeCharactersRepository = new HomeCharactersRepository();
        apiService = CommonApiUrl.getGeoJsonData();
//        datosmutable = new MutableLiveData<>();
    }
    public LiveData<RickMortyResponse> getHomeCharactersLiveData(int page){
        return homeCharactersRepository.getCharactersRepo(page);
    }

    public MutableLiveData<RickMortyResponse> getRMCharactersListObserver() {
        if (datosmutable == null) {
            datosmutable = new MutableLiveData<>();
        }
        return datosmutable;
    }



    public void apiCallCharacters(int page) {
        apiService.getCharacters(page).enqueue(new Callback<RickMortyResponse>() {
            @Override
            public void onResponse( Call<RickMortyResponse> call, Response<RickMortyResponse> response) {
                AppLogger.e("api call");
                datosmutable.postValue(response.body());
            }
            @Override
            public void onFailure( Call<RickMortyResponse> call, @NonNull Throwable t) {
                datosmutable.postValue(null);
            }
        });
    }

//    public MutableLiveData<String> getCurrentName() {
//        if (currentName == null) {
//            currentName = new MutableLiveData<String>();
//        }
//        return currentName;
//    }
}


