package com.example.rayfitnessapp;

public class Exercise {
    private int id;
    private String name;
    private String description;
    private String durationOrSets;
    private String gifPath;

    public Exercise(int id, String name, String description, String durationOrSets, String gifPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationOrSets = durationOrSets;
        this.gifPath = gifPath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDurationOrSets() {
        return durationOrSets;
    }

    public String getGifPath() {
        return gifPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationOrSets(String durationOrSets) {
        this.durationOrSets = durationOrSets;
    }

    public void setGifPath(String gifPath) {
        this.gifPath = gifPath;
    }
}
