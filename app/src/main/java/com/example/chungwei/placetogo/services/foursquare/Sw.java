package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Sw implements Serializable {
    private double lat;

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private double lng;

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
