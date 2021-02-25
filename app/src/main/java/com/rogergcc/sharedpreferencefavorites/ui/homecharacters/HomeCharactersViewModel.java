/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui.homecharacters;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rogergcc.sharedpreferencefavorites.model.RickMorty;
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
    MutableLiveData<RickMorty> listaCharacterDetails;
    private IApiService apiService;
    public HomeCharactersViewModel () {
        homeCharactersRepository = new HomeCharactersRepository();
        apiService = CommonApiUrl.getGeoJsonData();
        //homeCharactersRepository.getCharactersRepo(0);


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



    public void getCharactersV() {
        apiService.getCharacters(0).enqueue(new Callback<RickMortyResponse>() {
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

    public void apiCallCharacters(int page) {
        apiService.getCharacters(page).enqueue(new Callback<RickMortyResponse>() {
            @Override
            public void onResponse( Call<RickMortyResponse> call, Response<RickMortyResponse> response) {
                AppLogger.e("api call =>PAGE Nº"+page);
                datosmutable.postValue(response.body());
            }
            @Override
            public void onFailure( Call<RickMortyResponse> call, @NonNull Throwable t) {
                datosmutable.postValue(null);
            }
        });
    }

    public MutableLiveData<RickMorty> getDetailsListOserver() {
        if (listaCharacterDetails == null) {
            listaCharacterDetails = new MutableLiveData<>();
        }
        return listaCharacterDetails;
    }

    public void apiCallDetailsCharacter(String codigo) {

        apiService.getCharacterDetails(Integer.parseInt(codigo)).enqueue(new Callback<RickMorty>() {
            @Override
            public void onResponse(Call<RickMorty> call, Response<RickMorty> response) {
                if (response.body() == null) return;
                listaCharacterDetails.postValue(response.body());
            }

            @Override
            public void onFailure(Call<RickMorty> call, Throwable t) {
                listaCharacterDetails.postValue(null);
                AppLogger.e("Error: " + t.getMessage());
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


