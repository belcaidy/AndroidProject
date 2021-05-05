package com.example.androidproject;

import java.io.Serializable;

public class Note implements Serializable {
    private String label;
    private double score;

    public Note(String label, double score) {
        this.label = label;
        this.score = score;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
