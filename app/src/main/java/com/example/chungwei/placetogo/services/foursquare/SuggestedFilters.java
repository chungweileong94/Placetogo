package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;
import java.util.ArrayList;

public class SuggestedFilters implements Serializable {
    private String header;

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    private ArrayList<Filter> filters;

    public ArrayList<Filter> getFilters() {
        return this.filters;
    }

    public void setFilters(ArrayList<Filter> filters) {
        this.filters = filters;
    }
}
