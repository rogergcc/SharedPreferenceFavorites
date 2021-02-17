package com.rogergcc.sharedpreferencefavorites.remote;

public class CommonApiUrl {
    //    public static User currentUser;

    private static final String API_ENDPOINT = "https://rickandmortyapi.com/api/";


    public static String convertCodeToStatus(String code) {

        switch (code) {
            case "0":
                return "Placed";
            case "1":
                return "Preparing Orders";
            case "2":
                return "Shipping";
            default:
                return "Delivered";
        }
    }


    public static IApiService getGeoJsonData() {
        return RetrofitClient.getClienteGson(API_ENDPOINT).create(IApiService.class);
    }
}
