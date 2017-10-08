package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;
import java.util.ArrayList;

public class HereNow implements Serializable {
    private int count;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String summary;

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    protected ArrayList<CheckInGroup> groups;

    public ArrayList<CheckInGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(ArrayList<CheckInGroup> groups) {
        this.groups = groups;
    }
}
