package com.example.victorianadjar.myproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface DogRestApi {
    //@GET("list/all")
    @Headers("x-api-key: 62fce491-76b1-4e8e-af47-504d6e19e8bf")
    @GET("v1/breeds")
    Call<List<Breed>> getListBreed();

    @Headers("x-api-key: 62fce491-76b1-4e8e-af47-504d6e19e8bf")
    @GET("v1/images/search")
    Call<List<Image>> getImage(@Query("breed_ids") int id);
}
