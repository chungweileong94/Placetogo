package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Filter implements Serializable {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String key;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
