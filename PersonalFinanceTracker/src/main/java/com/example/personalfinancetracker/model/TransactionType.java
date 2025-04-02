package com.example.personalfinancetracker.model;

public enum TransactionType {
    INCOME,
    EXPENSE;

    public String getDisplayName() {
        return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
    }
}
