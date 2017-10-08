package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Result implements Serializable {
    private Meta meta;

    public Meta getMeta() {
        return this.meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    private Response response;

    public Response getResponse() {
        return this.response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}


