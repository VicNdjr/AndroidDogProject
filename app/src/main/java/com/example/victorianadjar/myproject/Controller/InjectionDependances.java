package com.example.victorianadjar.myproject.Controller;

import com.example.victorianadjar.myproject.DogRestApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InjectionDependances {


    public static Gson getGsonDependances (){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    public static Retrofit getRetrofitDependances(Gson gson){
        return new Retrofit.Builder()
                .baseUrl(DogRestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


}
