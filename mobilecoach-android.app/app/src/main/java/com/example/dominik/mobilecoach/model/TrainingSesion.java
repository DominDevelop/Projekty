package com.example.dominik.mobilecoach.model;

/**
 * Created by Dominik on 2015-10-01.
 */
public class TrainingSesion {

    private int id;
    private float weight;
    private long time;
    private int calories;
    private float distance;
    private float speed;
    private String date;

    public TrainingSesion() {
    }

    public TrainingSesion(int id, float weight, int calories, float distance, float speed, String date,long time) {

        this.id = id;
        this.weight = weight;
        this.calories = calories;
        this.distance = distance;
        this.speed = speed;
        this.date = date;
        this.time = time;
    }

    /*
    Settery
     */

    public void setTime(long time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /*
    Gettery
     */

    public long getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDistance() {
        return distance;
    }

    public int getCalories() {
        return calories;
    }

    public float getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

}
