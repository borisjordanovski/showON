package com.example.boris.showon.model;

/**
 * Created by Boris on 21-Jul-17.
 */

public class TVShowImage {
    private String medium;
    private String original;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public TVShowImage(){}

    public TVShowImage(String medium, String original) {

        this.medium = medium;
        this.original = original;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
