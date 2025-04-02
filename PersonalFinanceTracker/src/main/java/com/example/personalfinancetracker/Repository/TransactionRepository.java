package com.example.personalfinancetracker.Repository;

import com.example.personalfinancetracker.model.Transaction;
import com.example.personalfinancetracker.model.TransactionType;
import com.example.personalfinancetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    // Find transactions by user
    List<Transaction> findByUserId(int userId);

    // Find transactions by type (income or expense)
    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByUser(User user);
}
