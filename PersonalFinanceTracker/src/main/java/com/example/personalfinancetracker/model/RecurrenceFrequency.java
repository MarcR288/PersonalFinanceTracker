package com.example.personalfinancetracker.model;

public enum RecurrenceFrequency {
    MONTHLY;
    public String getDisplayName() {
        return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
    }
}
