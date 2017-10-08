package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;
import java.util.ArrayList;

public class Reasons implements Serializable {
    private int count;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private ArrayList<Item2> items;

    public ArrayList<Item2> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<Item2> items) {
        this.items = items;
    }
}
