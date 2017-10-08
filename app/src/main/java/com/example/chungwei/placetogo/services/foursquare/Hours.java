package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Hours implements Serializable {
    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private RichStatus richStatus;

    public RichStatus getRichStatus() {
        return this.richStatus;
    }

    public void setRichStatus(RichStatus richStatus) {
        this.richStatus = richStatus;
    }

    private boolean isOpen;

    public boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    private boolean isLocalHoliday;

    public boolean getIsLocalHoliday() {
        return this.isLocalHoliday;
    }

    public void setIsLocalHoliday(boolean isLocalHoliday) {
        this.isLocalHoliday = isLocalHoliday;
    }
}
