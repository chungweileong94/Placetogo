package com.example.chungwei.placetogo.services.foursquare.models;

public class VenuePhotoResult {
    private Meta meta;

    public Meta getMeta() {
        return this.meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    private VenuePhotosResponse response;

    public VenuePhotosResponse getResponse() {
        return this.response;
    }

    public void setResponse(VenuePhotosResponse response) {
        this.response = response;
    }
}
