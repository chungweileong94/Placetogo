package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Tip implements Serializable {
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int createdAt;

    public int getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    private String text;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String canonicalUrl;

    public String getCanonicalUrl() {
        return this.canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    private Photo photo;

    public Photo getPhoto() {
        return this.photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    private String photourl;

    public String getPhotourl() {
        return this.photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    private Likes likes;

    public Likes getLikes() {
        return this.likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    private boolean logView;

    public boolean getLogView() {
        return this.logView;
    }

    public void setLogView(boolean logView) {
        this.logView = logView;
    }

    private int agreeCount;

    public int getAgreeCount() {
        return this.agreeCount;
    }

    public void setAgreeCount(int agreeCount) {
        this.agreeCount = agreeCount;
    }

    private int disagreeCount;

    public int getDisagreeCount() {
        return this.disagreeCount;
    }

    public void setDisagreeCount(int disagreeCount) {
        this.disagreeCount = disagreeCount;
    }

    private Todo todo;

    public Todo getTodo() {
        return this.todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    private User user;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
