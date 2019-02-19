package com.example.victorianadjar.myproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DogRestApi {
    //@GET("list/all")
    @GET("v1/breeds")
    Call<List<Breed>> getListBreed();

    @GET("v1/breeds/{id}")
    Call<Breed> getDetails(@Path("id") int id );
}
