package com.example.personalfinancetracker.model;

// Enum for various Expense Tags
public enum ExpenseTag {
    HOME,
    UTILITIES,
    CAR,
    TRANSPORT,
    ENTERTAINMENT,
    FOOD,
    OTHER;

    public String getDisplayName() {
        return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
    }
}
