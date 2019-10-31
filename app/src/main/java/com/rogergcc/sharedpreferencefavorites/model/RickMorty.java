/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RickMorty {
    @SerializedName("created")
    private String created;
    @SerializedName("url")
    private String url;
    @SerializedName("episode")
    private List<String> episode;
    @SerializedName("image")
    private String image;
    @SerializedName("location")
    private Location location;
    @SerializedName("origin")
    private Origin origin;
    @SerializedName("gender")
    private String gender;
    @SerializedName("type")
    private String type;
    @SerializedName("species")
    private String species;
    @SerializedName("status")
    private String status;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public void setEpisode(List<String> episode) {
        this.episode = episode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
