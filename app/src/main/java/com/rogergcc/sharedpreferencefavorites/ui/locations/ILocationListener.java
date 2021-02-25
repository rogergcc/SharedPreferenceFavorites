/*
 * Copyright (c) 2020. rogergcc
 */

package com.rogergcc.sharedpreferencefavorites.ui.locations;

import android.widget.ImageView;

import com.rogergcc.sharedpreferencefavorites.remote.model.LocationResponse;

public interface ILocationListener {

    void onDashboardCourseClick(LocationResponse locationResponse, ImageView imageView); // Shoud use imageview to make the shared animation between the two activity
    void ondLocationClick(LocationResponse.Location locationResponse); // Shoud use imageview to make the shared animation between the two activity

}
