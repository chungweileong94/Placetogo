package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class SuggestedBounds implements Serializable {
    private Ne ne;

    public Ne getNe() {
        return this.ne;
    }

    public void setNe(Ne ne) {
        this.ne = ne;
    }

    private Sw sw;

    public Sw getSw() {
        return this.sw;
    }

    public void setSw(Sw sw) {
        this.sw = sw;
    }
}
