package com.example.personalfinancetracker.Controller;

import com.example.personalfinancetracker.DTO.DashboardDTO;
import com.example.personalfinancetracker.Service.RecurringTransactionService;
import com.example.personalfinancetracker.Service.TransactionService;
import com.example.personalfinancetracker.model.*;
import com.example.personalfinancetracker.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class FinanceController {

    private final UserService userService;
    private final TransactionService transactionService;
    private final RecurringTransactionService recurringTransactionService;

    public FinanceController(UserService userService, TransactionService transactionService, RecurringTransactionService recurringTransactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.recurringTransactionService = recurringTransactionService;
    }

    // Get mapping for dashbaord with overview
    @GetMapping("/dashboard")
    public String userDashboard(Model model) {
        User user = userService.getCurrentUser();
        // Extract the username from the User object and add the username to the model
        String username = user.getUsername();
        model.addAttribute("username", username);
        model.addAttribute("user", user);

        List<Transaction> transactions = transactionService.getTransactionsByUser(user.getId());
        List<RecurringTransaction> recurringTransactions = recurringTransactionService.getRecurringTransactionByUser(user.getId());

        // Initialize total income and total expense as BigDecimal
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        // Sum income and expenses using BigDecimal for precision
        for (RecurringTransaction recurringTransaction : recurringTransactions) {
            BigDecimal amount = recurringTransaction.getAmount();
            if (recurringTransaction.getType() == TransactionType.INCOME) {
                totalIncome = totalIncome.add(amount);
            } else if (recurringTransaction.getType() == TransactionType.EXPENSE) {
                totalExpense = totalExpense.add(amount);
            }
        }

        // Calculate balance as the difference between income and expense
        BigDecimal balance = totalIncome.subtract(totalExpense);

        // Create the DTO and add it to the model
        DashboardDTO dashboardDTO = new DashboardDTO(transactions, recurringTransactions);
        model.addAttribute("dashboard", dashboardDTO);

        // Add totalIncome, totalExpense, and balance to the model
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("balance", balance);

        // Add BigDecimal.ZERO to model for comparison in Thymeleaf
        model.addAttribute("bigDecimalZero", BigDecimal.ZERO);
        return "dashboard";
    }

//    // Shows the transactions
//    @GetMapping("/transactions")
//    public String transactions(Model model) {
//        User user = userService.getCurrentUser();
//        return "transactions";
//    }

    // Shows the Add transaction form
    @GetMapping("/transactions/add")
    public String addTransaction(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("transaction", new Transaction());

        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("ExpenseTags", ExpenseTag.values());
        return "transactions";
    }

    // Records a One-Time transaction
    @PostMapping("/transactions/add")
    public String addTransaction(@ModelAttribute Transaction transaction, Model model) {
        User currentUser = userService.getCurrentUser();
        transactionService.addTransaction(currentUser, transaction);
        return "redirect:/dashboard";
    }

    // Shows the form to add a recurring transaction
    @GetMapping("/recurring-transactions/add")
    public String recurringTransactions(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("recurringTransaction", new RecurringTransaction());
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("ExpenseTags", ExpenseTag.values());
        model.addAttribute("RecurrenceFrequency", RecurrenceFrequency.values());
        return "recurringTransactions";
    }

    // Records a recurring transaction
    @PostMapping("/recurring-transactions/add")
    public String addRecurringTransaction(@ModelAttribute RecurringTransaction recurringTransaction, Model model) {
        User currentUser = userService.getCurrentUser();
        recurringTransactionService.addRecurringTransaction(currentUser, recurringTransaction);
        return "redirect:/dashboard";
    }

    // Get mapping for Overview
    @GetMapping("/overview")
    public String overview(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);

        List<Transaction> transactions = transactionService.getTransactionsByUser(user.getId());
        List<RecurringTransaction> recurringTransactions = recurringTransactionService.getRecurringTransactionByUser(user.getId());

        // Initialize total income and total expense as BigDecimal
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        // Sum income and expenses using BigDecimal for precision
        for (RecurringTransaction recurringTransaction : recurringTransactions) {
            BigDecimal amount = recurringTransaction.getAmount(); // Already a BigDecimal, no need to convert
            if (recurringTransaction.getType() == TransactionType.INCOME) {
                totalIncome = totalIncome.add(amount); // Add to income
            } else if (recurringTransaction.getType() == TransactionType.EXPENSE) {
                totalExpense = totalExpense.add(amount); // Add to expense
            }
        }

        // Calculate balance as the difference between income and expense
        BigDecimal balance = totalIncome.subtract(totalExpense);

        // Create the DTO and add it to the model
        DashboardDTO dashboardDTO = new DashboardDTO(transactions, recurringTransactions);
        model.addAttribute("dashboard", dashboardDTO);

        // Add totalIncome, totalExpense, and balance to the model
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("balance", balance);

        // Add BigDecimal.ZERO to model for comparison in Thymeleaf
        model.addAttribute("bigDecimalZero", BigDecimal.ZERO);

        return "overview"; // Return the 'overview.html' Thymeleaf template
    }

//    // Get mapping for transactions list - No longer needed, added capability to delete a transaction at /overview
//    @GetMapping("/transactions/list")
//    public String transactionsList(Model model) {
//        User user = userService.getCurrentUser();
//        model.addAttribute("user", user);
//        List<Transaction> transactions = transactionService.getTransactionsByUser(user.getId());
//        List<RecurringTransaction> recurringTransactions = recurringTransactionService.getRecurringTransactionByUser(user.getId());
//        model.addAttribute("transactions", transactions);
//        model.addAttribute("recurringTransactions", recurringTransactions);
//        return "transactionsList";
//    }

    // Deletes a transaction
    @PostMapping("/transactions/delete/{transactionId}")
    public String deleteTransaction(@PathVariable("transactionId") int transactionId) {
        transactionService.deleteTransaction(transactionId); // Delete the regular transaction
        return "redirect:/overview"; // Redirect to the list after deletion
    }

    // Deletes a recurring-transaction
    @PostMapping("/recurring-transactions/delete/{recTransactionId}")
    public String deleteRecurringTransaction(@PathVariable("recTransactionId") int recTransactionId) {
        recurringTransactionService.deleteRecurringTransaction(recTransactionId); // Delete the recurring transaction
        return "redirect:/overview"; // Redirect to the list after deletion
    }
}
