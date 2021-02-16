package com.rogergcc.sharedpreferencefavorites.remote.model;

/**
 * Created by dell on 4/26/2018.
 */

public class Notification {
    public String body;
    public String title;
    public String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public Notification(String body, String title, String date) {
        this.body = body;
        this.title = title;
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
