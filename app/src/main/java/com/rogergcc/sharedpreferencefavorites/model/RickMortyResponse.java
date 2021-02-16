/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RickMortyResponse {
    @SerializedName("info")
    private Info info;
    @SerializedName("results")
    private List<RickMorty> results;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<RickMorty> getResults() {
        return results;
    }

    public void setResults(List<RickMorty> results) {
        this.results = results;
    }
    //https://rickandmortyapi.com/api/character/


    @Override
    public String toString() {
        return "RickMortyResponse{" +
                "info=" + info +
                ", results=" + results +
                '}';
    }

}
