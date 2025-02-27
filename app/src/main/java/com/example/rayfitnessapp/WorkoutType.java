package com.example.rayfitnessapp;

public class WorkoutType {

    private int id;
    private String name;
    private String duration;
    private String coverPhoto;

    public WorkoutType(int id, String name, String duration, String coverPhoto) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.coverPhoto = coverPhoto;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }
}
