package com.example.victorianadjar.myproject.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.victorianadjar.myproject.Controller.MyController;
import com.example.victorianadjar.myproject.Model.Breed;
import com.example.victorianadjar.myproject.Controller.OnItemClickListener;
import com.example.victorianadjar.myproject.R;

import java.util.List;

//penser à organiser le projet (pas tout mettre dans la racine) + le code dans main activity (faire des methodes)
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_view);
        MyController myController = new MyController(this);
        myController.getList();
    }

    public void showList(List<Breed> list) {
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
