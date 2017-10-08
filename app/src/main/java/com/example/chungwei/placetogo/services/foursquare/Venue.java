package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;
import java.util.ArrayList;

public class Venue implements Serializable {
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Contact contact;

    public Contact getContact() {
        return this.contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    private Location location;

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    private boolean verified;

    public boolean getVerified() {
        return this.verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    private Stats stats;

    public Stats getStats() {
        return this.stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private Price price;

    public Price getPrice() {
        return this.price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    private double rating;

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    private String ratingColor;

    public String getRatingColor() {
        return this.ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    private int ratingSignals;

    public int getRatingSignals() {
        return this.ratingSignals;
    }

    public void setRatingSignals(int ratingSignals) {
        this.ratingSignals = ratingSignals;
    }

    private boolean allowMenuUrlEdit;

    public boolean getAllowMenuUrlEdit() {
        return this.allowMenuUrlEdit;
    }

    public void setAllowMenuUrlEdit(boolean allowMenuUrlEdit) {
        this.allowMenuUrlEdit = allowMenuUrlEdit;
    }

    private BeenHere beenHere;

    public BeenHere getBeenHere() {
        return this.beenHere;
    }

    public void setBeenHere(BeenHere beenHere) {
        this.beenHere = beenHere;
    }

    private Hours hours;

    public Hours getHours() {
        return this.hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    private Photos photos;

    public Photos getPhotos() {
        return this.photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    private VenuePage venuePage;

    public VenuePage getVenuePage() {
        return this.venuePage;
    }

    public void setVenuePage(VenuePage venuePage) {
        this.venuePage = venuePage;
    }

    protected HereNow hereNow;

    public HereNow getHereNow() {
        return this.hereNow;
    }

    public void setHereNow(HereNow hereNow) {
        this.hereNow = hereNow;
    }
}
