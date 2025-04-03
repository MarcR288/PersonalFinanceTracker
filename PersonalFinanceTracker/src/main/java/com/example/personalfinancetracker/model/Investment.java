package com.example.personalfinancetracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;


@Entity
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ticker;
    private String name;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal currentValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Investment() {}

    public Investment(String ticker, String name, BigDecimal quantity, BigDecimal price, BigDecimal currentValue, User user) {
        this.ticker = ticker;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.currentValue = currentValue;
        this.user = user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }



    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Method to calculate current value based on quantity and price
    public void calculateCurrentValue() {
        if (this.quantity != null && this.price != null) {
            this.currentValue = this.quantity.multiply(this.price);
        }
    }
}
