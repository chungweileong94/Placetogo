package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    private Reasons reasons;

    public Reasons getReasons() {
        return this.reasons;
    }

    public void setReasons(Reasons reasons) {
        this.reasons = reasons;
    }

    private Venue venue;

    public Venue getVenue() {
        return this.venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    private ArrayList<Tip> tips;

    public ArrayList<Tip> getTips() {
        return this.tips;
    }

    public void setTips(ArrayList<Tip> tips) {
        this.tips = tips;
    }

    private String referralId;

    public String getReferralId() {
        return this.referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }
}
