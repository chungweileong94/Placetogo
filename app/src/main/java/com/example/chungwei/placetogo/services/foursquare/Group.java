package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ArrayList<Item> items;

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
