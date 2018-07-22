package com.example.farahalkiswani.for9a;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Farah alkiswani on 7/8/2018.
 */

public class ApiClient {
    public static final String BASE_URL = "https://api.for9a.com/";

    private static Retrofit retrofit;



    public static Retrofit getClient() {
        if (retrofit==null)
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
