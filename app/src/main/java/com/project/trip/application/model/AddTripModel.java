package com.project.trip.application.model;

import com.google.firebase.database.Exclude;

public class AddTripModel {
    private String title;
    private String imageURL;

    public AddTripModel() {
    }


    public AddTripModel( String title, String imageURL) {
        this.title = title;
        this.imageURL = imageURL;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


}
