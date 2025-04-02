package com.example.personalfinancetracker.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class RecurringTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal amount;
    private String description;
    private LocalDate nextDueDate;

    @Enumerated(EnumType.STRING)
    private RecurrenceFrequency frequency;

    @Enumerated(EnumType.STRING)
    private ExpenseTag tag;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public RecurringTransaction() {}

    public RecurringTransaction(BigDecimal amount, String description, LocalDate nextDueDate, RecurrenceFrequency frequency, ExpenseTag tag, TransactionType type, User user) {
        this.amount = amount;
        this.description = description;
        this.nextDueDate = nextDueDate;
        this.frequency = frequency;
        this.tag = tag;
        this.type = type;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public RecurrenceFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(RecurrenceFrequency frequency) {
        this.frequency = frequency;
    }

    public ExpenseTag getTag() {
        return tag;
    }

    public void setTag(ExpenseTag tag) {
        this.tag = tag;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
