/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.model;

import com.google.gson.annotations.SerializedName;

public class Info {
    @SerializedName("prev")
    private String prev;
    @SerializedName("next")
    private String next;
    @SerializedName("pages")
    private int pages;
    @SerializedName("count")
    private int count;

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
