package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Item2 implements Serializable {
    private String summary;

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String reasonName;

    public String getReasonName() {
        return this.reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }
}
