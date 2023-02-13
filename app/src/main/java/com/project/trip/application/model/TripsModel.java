package com.project.trip.application.model;

import com.google.firebase.database.Exclude;

public class TripsModel {
    String nameTrips;
    String date;
    String numbers;

    private String key;

    public TripsModel() {
    }

    public TripsModel(String nameTrips, String date, String numbers) {
        this.nameTrips = nameTrips;
        this.date = date;
        this.numbers = numbers;
    }

    public String getNameTrips() {
        return nameTrips;
    }

    public void setNameTrips(String nameTrips) {
        this.nameTrips = nameTrips;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
