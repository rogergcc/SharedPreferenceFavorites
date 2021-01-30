package com.rogergcc.sharedpreferencefavorites.helpers;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by rogergcc on 31/08/2020.
 * Copyright Ⓒ 2020 . All rights reserved.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private int mCurrentPosition;
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void clear();

    public void bindViews(int position) {
        mCurrentPosition = position;
        clear();
    }
    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}

