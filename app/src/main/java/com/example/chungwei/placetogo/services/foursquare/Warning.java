package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Warning implements Serializable {
    private String text;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
