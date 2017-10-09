package com.example.chungwei.placetogo.services.foursquare.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Likes implements Serializable {
    private int count;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private ArrayList<String> groups;

    public ArrayList<String> getGroups() {
        return this.groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    private String summary;

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
