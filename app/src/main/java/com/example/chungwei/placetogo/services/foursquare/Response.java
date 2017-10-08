package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
    private SuggestedFilters suggestedFilters;

    public SuggestedFilters getSuggestedFilters() {
        return this.suggestedFilters;
    }

    public void setSuggestedFilters(SuggestedFilters suggestedFilters) {
        this.suggestedFilters = suggestedFilters;
    }

    private Warning warning;

    public Warning getWarning() {
        return this.warning;
    }

    public void setWarning(Warning warning) {
        this.warning = warning;
    }

    private int suggestedRadius;

    public int getSuggestedRadius() {
        return this.suggestedRadius;
    }

    public void setSuggestedRadius(int suggestedRadius) {
        this.suggestedRadius = suggestedRadius;
    }

    private String headerLocation;

    public String getHeaderLocation() {
        return this.headerLocation;
    }

    public void setHeaderLocation(String headerLocation) {
        this.headerLocation = headerLocation;
    }

    private String headerFullLocation;

    public String getHeaderFullLocation() {
        return this.headerFullLocation;
    }

    public void setHeaderFullLocation(String headerFullLocation) {
        this.headerFullLocation = headerFullLocation;
    }

    private String headerLocationGranularity;

    public String getHeaderLocationGranularity() {
        return this.headerLocationGranularity;
    }

    public void setHeaderLocationGranularity(String headerLocationGranularity) {
        this.headerLocationGranularity = headerLocationGranularity;
    }

    private String query;

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private int totalResults;

    public int getTotalResults() {
        return this.totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    private SuggestedBounds suggestedBounds;

    public SuggestedBounds getSuggestedBounds() {
        return this.suggestedBounds;
    }

    public void setSuggestedBounds(SuggestedBounds suggestedBounds) {
        this.suggestedBounds = suggestedBounds;
    }

    private ArrayList<Group> groups;

    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
}
