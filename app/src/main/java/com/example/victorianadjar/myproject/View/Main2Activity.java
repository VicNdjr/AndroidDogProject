package com.example.victorianadjar.myproject.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victorianadjar.myproject.Controller.MyController;
import com.example.victorianadjar.myproject.Model.Image;
import com.example.victorianadjar.myproject.Model.Breed;
import com.example.victorianadjar.myproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MyController myController = new MyController(this);
        myController.getItem();
    }

    public void showList(List<Image> list) {
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
