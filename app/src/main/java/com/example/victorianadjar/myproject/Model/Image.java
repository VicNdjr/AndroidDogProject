package com.example.victorianadjar.myproject.Model;

import com.example.victorianadjar.myproject.Model.Breed;

import java.util.List;

public class Image {
    private List<Breed> breeds;
    private String id;
    private String url;

    public List<Breed> getBreeds() {
        return breeds;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
