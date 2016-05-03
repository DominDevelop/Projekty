package com.example.dominik.mobilecoach.model;

/**
 * Created by Dominik on 2015-10-10.
 */
public class TrainingPlan {

    private int id;
    private int week;
    private int day;
    private boolean accepted;
    private String date;

    public TrainingPlan(){}

    public TrainingPlan(int id, int week, int day, boolean accepted, String date) {
        this.id = id;
        this.week = week;
        this.day = day;
        this.accepted = accepted;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
