package com.example.victorianadjar.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

//penser à organiser le projet (pas tout mettre dans la racine) + le code dans main activity (faire des methodes)
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String PREFS = "list";
    SharedPreferences sharedPreferences;

    static final String BASE_URL = "https://api.thedogapi.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_view);
        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        //si j'ai de la donnée dans mon cache, je l'affiche
        if(sharedPreferences.contains(PREFS)) {
            String listJson = sharedPreferences.getString(PREFS, "");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Breed>>(){}.getType();
            List<Breed> list = gson.fromJson(listJson, listType);
            showList(list);
        }else{ //si je n'ai pas de donnée dans mon cache, j'appelle le serveur
            Gson gson = new GsonBuilder()
                .setLenient()
                .create();

            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

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
                    Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                    showList(list);
                }

                @Override
                public void onFailure(Call<List<Breed>> call, Throwable t) {

                }
            });
        }
    }

    private void showList(List<Breed> list) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(list, new OnItemClickListener() {
            @Override
            public void onItemClick(Breed item) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}
