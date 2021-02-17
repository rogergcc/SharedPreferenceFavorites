/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui.homecharacters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rogergcc.sharedpreferencefavorites.model.RickMortyResponse;

public class HomeCharactersViewModel extends ViewModel {
    private HomeCharactersRepository homeCharactersRepository;

    public HomeCharactersViewModel () {
        homeCharactersRepository = new HomeCharactersRepository();
    }

    public LiveData<RickMortyResponse> getHomeCharacters(int page){
        return homeCharactersRepository.getCharactersRepo(page);

    }
}
