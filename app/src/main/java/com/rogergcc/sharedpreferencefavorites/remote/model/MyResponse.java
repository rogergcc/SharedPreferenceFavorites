package com.rogergcc.sharedpreferencefavorites.remote.model;

import java.util.List;

/**
 * Created by dell on 4/26/2018.
 */

public class MyResponse {
    public long multicast_id;
    public int success;
    public int failure;
    public int canonical_ids;
    List<Result> results;

}
