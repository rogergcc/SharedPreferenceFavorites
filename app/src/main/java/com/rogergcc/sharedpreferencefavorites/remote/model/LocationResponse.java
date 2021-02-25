/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.remote.model;

import com.google.gson.annotations.SerializedName;
import com.rogergcc.sharedpreferencefavorites.model.Info;

import java.util.List;

public class LocationResponse {

    @SerializedName("results")
    private List<Location> results;
    @SerializedName("info")
    private Info info;

    public List<Location> getResults() {
        return results;
    }

    public void setResults(List<Location> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public static class Location {
        @SerializedName("created")
        private String created;
        @SerializedName("url")
        private String url;
        @SerializedName("residents")
        private List<String> residents;

        private List<String> residentsDisplayNames;

        @SerializedName("dimension")
        private String dimension;
        @SerializedName("type")
        private String type;
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

        public List<String> getResidents() {
            return residents;
        }

        public void setResidents(List<String> residents) {
            this.residents = residents;
        }

        public String getDimension() {
            return dimension;
        }

        public void setDimension(String dimension) {
            this.dimension = dimension;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public List<String> getResidentsDisplayNames() {
            return residentsDisplayNames;
        }

        public void setResidentsDisplayNames(List<String> residentsDisplayNames) {
            this.residentsDisplayNames = residentsDisplayNames;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "created='" + created + '\'' +
                    ", url='" + url + '\'' +
                    ", residents=" + residents +
                    ", residentsDisplayNames=" + residentsDisplayNames +
                    ", dimension='" + dimension + '\'' +
                    ", type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }


}
