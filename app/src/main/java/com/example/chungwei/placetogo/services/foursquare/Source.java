package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Source implements Serializable {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
