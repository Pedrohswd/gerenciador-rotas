package com.malmitas.backend.model.dtos;

public class DistanceDuration {
    private double distance; // Em km
    private double duration; // Em segundos

    public DistanceDuration(double distance, double duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public double getDuration() {
        return duration;
    }
}

