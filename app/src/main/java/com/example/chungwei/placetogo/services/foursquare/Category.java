package com.example.chungwei.placetogo.services.foursquare;

import java.io.Serializable;

public class Category implements Serializable {
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

    private String pluralName;

    public String getPluralName() {
        return this.pluralName;
    }

    public void setPluralName(String pluralName) {
        this.pluralName = pluralName;
    }

    private String shortName;

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    private Icon icon;

    public Icon getIcon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    private boolean primary;

    public boolean getPrimary() {
        return this.primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
