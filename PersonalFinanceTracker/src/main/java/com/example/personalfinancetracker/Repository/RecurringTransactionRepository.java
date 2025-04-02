package com.example.personalfinancetracker.Repository;

import com.example.personalfinancetracker.model.RecurringTransaction;
import com.example.personalfinancetracker.model.Transaction;
import com.example.personalfinancetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Integer> {

    List<RecurringTransaction> findByUserId(int userId);

    // Find transactions by type (income or expense)
    List<RecurringTransaction> findByType(TransactionType type);
}
