package com.example.victorianadjar.myproject.Controller;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.victorianadjar.myproject.DogRestApi;
import com.example.victorianadjar.myproject.Model.Breed;
import com.example.victorianadjar.myproject.Model.Image;
import com.example.victorianadjar.myproject.View.Main2Activity;
import com.example.victorianadjar.myproject.View.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class MyController {
    private static final String PREFS = "list";
    SharedPreferences sharedPreferences;
    MainActivity mainActivity;
    Main2Activity main2Activity;

    public MyController(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.main2Activity = null;
    }

    public MyController(Main2Activity main2Activity){
        this.main2Activity = main2Activity;
        this.mainActivity = null;
    }

    public void getList(){
        sharedPreferences = mainActivity.getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        //si j'ai de la donnée dans mon cache, je l'affiche
        if(sharedPreferences.contains(PREFS)) {
            String listJson = sharedPreferences.getString(PREFS, "");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Breed>>(){}.getType();
            List<Breed> list = gson.fromJson(listJson, listType);
            mainActivity.showList(list);
        }else{ //si je n'ai pas de donnée dans mon cache, j'appelle le serveur

            Gson gson = InjectionDependances.getGsonDependances();
            Retrofit retrofit = InjectionDependances.getRetrofitDependances(gson);
            DogRestApi dogRestApi = retrofit.create(DogRestApi.class);

            Call<List<Breed>> call = dogRestApi.getListBreed();

            call.enqueue(new Callback<List<Breed>>() {
                @Override
                public void onResponse(Call<List<Breed>> call, Response<List<Breed>> response) {
                    List<Breed> list = response.body();
                    Gson gson = new Gson();
                    String listJson = gson.toJson(list);
                    sharedPreferences
                            .edit()
                            .putString(PREFS, listJson)
                            .apply();
                    Toast.makeText(mainActivity.getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                    mainActivity.showList(list);
                }

                @Override
                public void onFailure(Call<List<Breed>> call, Throwable t) {

                }
            });
        }
    }

    public void getItem(){
        final int id = main2Activity.getIntent().getIntExtra("id",0);

        Gson gson = InjectionDependances.getGsonDependances();
        Retrofit retrofit = InjectionDependances.getRetrofitDependances(gson);
        DogRestApi dogRestApi = retrofit.create(DogRestApi.class);

        Call<List<Image>> call = dogRestApi.getImage(id);

        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                List<Image> breed = response.body();
                main2Activity.showList(breed);//list en parametre puis traitement des données dans showlist
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

            }
        });
    }
}
