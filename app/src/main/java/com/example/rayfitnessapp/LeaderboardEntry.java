package com.example.rayfitnessapp;

public class LeaderboardEntry {

    private String username;
    private int points;

    public LeaderboardEntry(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }
}
