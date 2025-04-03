package com.example.personalfinancetracker.Service;

import com.example.personalfinancetracker.Repository.RecurringTransactionRepository;
import com.example.personalfinancetracker.model.RecurrenceFrequency;
import com.example.personalfinancetracker.model.RecurringTransaction;
import com.example.personalfinancetracker.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RecurringTransactionService {

    private final RecurringTransactionRepository recurringTransactionRepository;


    public RecurringTransactionService(RecurringTransactionRepository recurringTransactionRepository) {
        this.recurringTransactionRepository = recurringTransactionRepository;
    }

    // Save a new recurring-transaction
    public void addRecurringTransaction(User user, RecurringTransaction recurringTransaction) {
        recurringTransaction.setUser(user);
        recurringTransactionRepository.save(recurringTransaction);
    }

    // Returns a list of all recurring transactions for a user
    public List<RecurringTransaction> getRecurringTransactionByUser(int userId) {
        return recurringTransactionRepository.findByUserId(userId);
    }

    // Deletes a recurring transaction
    public void deleteRecurringTransaction(int recurringTransactionId) {
        recurringTransactionRepository.deleteById(recurringTransactionId);
    }
}
