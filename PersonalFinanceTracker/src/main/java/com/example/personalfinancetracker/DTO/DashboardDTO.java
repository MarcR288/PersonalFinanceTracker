package com.example.personalfinancetracker.DTO;

import com.example.personalfinancetracker.model.RecurringTransaction;
import com.example.personalfinancetracker.model.Transaction;
import com.example.personalfinancetracker.model.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {
    private List<Transaction> transactions;
    private List<RecurringTransaction> recurringTransactions;

    // New field for balance
    private BigDecimal balance;

    // Constructor
    public DashboardDTO(List<Transaction> transactions, List<RecurringTransaction> recurringTransactions) {
        this.transactions = transactions;
        this.recurringTransactions = recurringTransactions;
        this.balance = calculateBalance(recurringTransactions); // Calculate balance
    }

    // Getter for transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Getter for recurringTransactions
    public List<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactions;
    }

    // Getter for balance
    public BigDecimal getBalance() {
        return balance;
    }

    // Calculate the balance from recurring transactions
    private BigDecimal calculateBalance(List<RecurringTransaction> recurringTransactions) {
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (RecurringTransaction recurringTransaction : recurringTransactions) {
            BigDecimal amount = recurringTransaction.getAmount();
            if (recurringTransaction.getType() == TransactionType.INCOME) {
                totalIncome = totalIncome.add(amount);
            } else if (recurringTransaction.getType() == TransactionType.EXPENSE) {
                totalExpense = totalExpense.add(amount);
            }
        }

        return totalIncome.subtract(totalExpense);
    }
}
