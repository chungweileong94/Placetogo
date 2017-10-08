package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Photo2 implements Serializable {
    private String prefix;

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    private String suffix;

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
