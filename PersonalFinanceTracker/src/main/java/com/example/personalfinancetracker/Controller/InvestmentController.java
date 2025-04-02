package com.example.personalfinancetracker.Controller;

import com.example.personalfinancetracker.Repository.InvestmentRepository;
import com.example.personalfinancetracker.Service.InvestmentService;
import com.example.personalfinancetracker.Service.UserService;
import com.example.personalfinancetracker.model.Investment;
import com.example.personalfinancetracker.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    private final UserService userService;

    private final InvestmentRepository investmentRepository;

    public InvestmentController(InvestmentService investmentService, UserService userService, com.example.personalfinancetracker.Repository.InvestmentRepository investmentRepository) {
        this.investmentService = investmentService;
        this.userService = userService;
        this.investmentRepository = investmentRepository;
    }

    @GetMapping
    public String showInvestments(Model model) {
        User user = userService.getCurrentUser();
        List<Investment> investments = investmentService.getAllInvestments(user.getId());
        model.addAttribute("investments", investments);
        return "investments";  // This will render investments.html
    }

    @PostMapping
    public String updateInvestments(Model model, @ModelAttribute("investments") Investment investment) {
        User user = userService.getCurrentUser();
        investment.setUser(user);
        List<Investment> investments = investmentService.getAllInvestments(user.getId());
        for (Investment inv : investments) {
            investmentService.updateInvestmentValue(inv);
            investmentService.updateInvestmentPrice(inv);
        }
        return "redirect:/investments";
    }

    @GetMapping("/add")
    public String showAddInvestmentForm(Model model) {
        model.addAttribute("investment", new Investment());
        return "add-investment";
    }

    // Handle the form submission
    @PostMapping("/add")
    public String addInvestment(Investment investment, Model model) {
        User currentUser = userService.getCurrentUser();  // Get the logged-in user
        investment.setUser(currentUser);

        // Calculate current value before saving
        investment.calculateCurrentValue();
        investment.setPrice(BigDecimal.ZERO);

        investmentService.addInvestment(investment);  // Save the investment
        model.addAttribute("message", "Investment added successfully!");
        return "redirect:/investments";  // Redirect to the same page after submission
    }

    //Delete an Investment
    @PostMapping("/delete/{id}")
    public String deleteInvestment(@PathVariable("id") int id) {
        // Find the investment by ID
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found"));

        // Delete the investment from the repository
        investmentService.deleteInvestment(investment);
        return "redirect:/investments";
    }
}
