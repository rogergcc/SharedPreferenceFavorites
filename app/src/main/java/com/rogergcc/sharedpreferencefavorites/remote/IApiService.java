package com.rogergcc.sharedpreferencefavorites.remote;


import com.rogergcc.sharedpreferencefavorites.model.RickMorty;
import com.rogergcc.sharedpreferencefavorites.model.RickMortyResponse;
import com.rogergcc.sharedpreferencefavorites.remote.model.LocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiService {

//    @FormUrlEncoded
//    @POST("enviarnotificacion.php")
//    Call<ResponseBody> sendnotificacion(@Field("nuevoToken") String email, @Field("imei") String password, @Field("appDetails") String appName);
//    Call<ResponseTestToken> getResponse(@Field("email") String email, @Field("password") String password) ;

    //Sending Json Object
//    @Headers("Content-Type: application/json")
//    @POST("restaurante")
//    Call<RickMortyResponse> postCharstttt(@Body Restaurante restaurante);

    @Headers("Content-Type: application/json")
    @GET("character")
    Call<RickMortyResponse> getCharacters(@Query("page") int page);

    @Headers("Content-Type: application/json")
    @GET("location")
    Call<LocationResponse> getLocations(@Query("page") int page);

    @Headers("Content-Type: application/json")
    @GET("character/{number}")
    Call<RickMorty> getCharacterDetails(@Path("number") int number);

    //region REGION FIELD URL encode METHOD
//    @FormUrlEncoded
//    @POST("/login")
//    Call<ResponseBody> getResponse(@Field("email") String email, @Field("password") String password) ;
}
