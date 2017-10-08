package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class User implements Serializable {
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String firstName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String gender;

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private Photo2 photo;

    public Photo2 getPhoto() {
        return this.photo;
    }

    public void setPhoto(Photo2 photo) {
        this.photo = photo;
    }
}
