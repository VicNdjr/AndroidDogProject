package com.example.victorianadjar.myproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.victorianadjar.myproject.MainActivity.BASE_URL;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final int id = getIntent().getIntExtra("id",0);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DogRestApi dogRestApi = retrofit.create(DogRestApi.class);

        Call<List<Image>> call = dogRestApi.getImage(id);

        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                List<Image> breed = response.body();
                showList(breed);//list en parametre puis traitement des données dans showlist
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

            }
        });


    }

    private void showList(List<Image> list) {
        Image image = list.get(0);
        Breed breed = image.getBreeds().get(0);
        TextView txt1 = findViewById(R.id.breed_name);
        TextView txt2 = findViewById(R.id.breed_group);
        TextView txt3 = findViewById(R.id.life_span);
        TextView txt4 = findViewById(R.id.temperament);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Picasso.with(getBaseContext()).load(image.getUrl()).fit().into(imageView);
        txt1.append(breed.getName());
        txt2.append(breed.getBreed_group());
        txt3.append(breed.getLife_span());
        txt4.append(breed.getTemperament());
    }
}
