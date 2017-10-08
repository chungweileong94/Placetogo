package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Stats implements Serializable {
    private int checkinsCount;

    public int getCheckinsCount() {
        return this.checkinsCount;
    }

    public void setCheckinsCount(int checkinsCount) {
        this.checkinsCount = checkinsCount;
    }

    private int usersCount;

    public int getUsersCount() {
        return this.usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    private int tipCount;

    public int getTipCount() {
        return this.tipCount;
    }

    public void setTipCount(int tipCount) {
        this.tipCount = tipCount;
    }
}
