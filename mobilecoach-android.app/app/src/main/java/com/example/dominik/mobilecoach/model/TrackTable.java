package com.example.dominik.mobilecoach.model;

/**
 * Created by Dominik on 2015-10-16.
 */
public class TrackTable {

    private int id;
    private int trainingID;
    private double longitude;
    private double latidude;
    private double speed;

    public TrackTable(){}

    public TrackTable(int id, int trainingID, double longitude, double latidude) {
        this.id = id;
        this.trainingID = trainingID;
        this.longitude = longitude;
        this.latidude = latidude;
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getTrainingID() {
        return trainingID;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatidude() {
        return latidude;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setTrainingID(int trainingID) {
        this.trainingID = trainingID;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatidude(double latidude) {
        this.latidude = latidude;
    }
}
