package com.example.chungwei.placetogo.services.foursquare.models;


import java.io.Serializable;
import java.util.ArrayList;

public class Photos implements Serializable {
    private int count;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private ArrayList<Photo> items;

    public ArrayList<Photo> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<Photo> items) {
        this.items = items;
    }

    private int dupesRemoved;

    public int getDupesRemoved() {
        return this.dupesRemoved;
    }

    public void setDupesRemoved(int dupesRemoved) {
        this.dupesRemoved = dupesRemoved;
    }
}
