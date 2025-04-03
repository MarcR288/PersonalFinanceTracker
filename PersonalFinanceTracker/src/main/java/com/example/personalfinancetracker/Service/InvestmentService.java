package com.example.personalfinancetracker.Service;

import com.example.personalfinancetracker.Repository.InvestmentRepository;
import com.example.personalfinancetracker.model.Investment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final StockPriceService stockPriceService;

    public InvestmentService(InvestmentRepository investmentRepository, StockPriceService stockPriceService) {
        this.investmentRepository = investmentRepository;
        this.stockPriceService = stockPriceService;
    }

    // Save a new investment
    public Investment addInvestment(Investment investment) {
        investment.setCurrentValue(investment.getQuantity().multiply(investment.getPrice()));
        investmentRepository.save(investment);
        return investment;
    }

    // Setting the current value of a stock based on quantity and price
    public Investment updateInvestmentValue(Investment investment) {
        BigDecimal currentPrice = stockPriceService.getCurrentStockPrice(investment.getTicker());
        investment.setCurrentValue(investment.getQuantity().multiply(currentPrice));
        return investmentRepository.save(investment);
    }

    // Sets the current price of a stock
    public Investment updateInvestmentPrice(Investment investment) {
        BigDecimal currentPrice = stockPriceService.getCurrentStockPrice(investment.getTicker());
        investment.setPrice(currentPrice);
        return investmentRepository.save(investment);
    }

    // Update the price of investments
    public void updateAllInvestments() {
        List<Investment> investments = investmentRepository.findAll();
        for (Investment investment : investments) {
            BigDecimal currentPrice = stockPriceService.getCurrentStockPrice(investment.getTicker());
            investment.setCurrentValue(investment.getQuantity().multiply(currentPrice));
            investmentRepository.save(investment);
        }
    }

    // Return a list of investments by userid
    public List<Investment> getAllInvestments(int userID) {
        return investmentRepository.findByUserId(userID);
    }

    // Delete an investment
    public void deleteInvestment(Investment investment) {
        investmentRepository.delete(investment);
    }
}
