package com.example.personalfinancetracker.Service;

import com.example.personalfinancetracker.Repository.TransactionRepository;
import com.example.personalfinancetracker.model.Transaction;
import com.example.personalfinancetracker.model.TransactionType;
import com.example.personalfinancetracker.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;


    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Add a transaction
    public Transaction addTransaction(User user, Transaction transaction) {
        // Ensure the transaction is associated with the correct user
        transaction.setUser(user);
        return transactionRepository.save(transaction);
    }

    // Return a list of transactions of a user
    public List<Transaction> getTransactionsByUser(int userId) {
        return transactionRepository.findByUserId(userId);
    }

    // Find transactions by Type - Not implemented
    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    // Delete a transaction
    public void deleteTransaction(int id) {
        transactionRepository.deleteById(id);
    }
}
