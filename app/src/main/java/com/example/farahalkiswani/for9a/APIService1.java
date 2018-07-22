package com.example.farahalkiswani.for9a;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Farah alkiswani on 7/8/2018.
 */

public interface APIService1 {
    @FormUrlEncoded
    @POST("/user/login")
    Call<JsonObject> userLogIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/user/forget-password")
    Call<JsonObject> userChangePassword(@Field("email") String email);

    @Headers("authentication: MNrAG2QLv41sWs2qd-spRKT594bfwROM")
    @GET("/user/timeline")
    Call<JsonObject> getTimeline(@Query("count") Integer count, @Query("since_id") Integer since_id);

}
