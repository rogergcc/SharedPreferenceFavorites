/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui.homecharacters;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rogergcc.sharedpreferencefavorites.model.RickMortyResponse;
import com.rogergcc.sharedpreferencefavorites.remote.CommonApiUrl;
import com.rogergcc.sharedpreferencefavorites.remote.IApiService;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeCharactersRepository {
    private IApiService apiService;

    public HomeCharactersRepository() {
        apiService = CommonApiUrl.getGeoJsonData();

    }

    public LiveData<RickMortyResponse> getCharactersRepo(int page) {
        MutableLiveData<RickMortyResponse> data = new MutableLiveData<>();

        apiService.getCharacters(page).enqueue(new Callback<RickMortyResponse>() {
            @Override
            public void onResponse(@NonNull Call<RickMortyResponse> call, retrofit2.Response<RickMortyResponse> response) {
                if (response.body() == null) return;
                data.setValue(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<RickMortyResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
