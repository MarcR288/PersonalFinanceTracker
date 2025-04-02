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

    public Investment addInvestment(Investment investment) {
        investment.setCurrentValue(investment.getQuantity().multiply(investment.getPrice()));
        investmentRepository.save(investment);
        return investment;
    }

    public Investment updateInvestmentValue(Investment investment) {
        BigDecimal currentPrice = stockPriceService.getCurrentStockPrice(investment.getTicker());
        investment.setCurrentValue(investment.getQuantity().multiply(currentPrice));
        return investmentRepository.save(investment);
    }

    public Investment updateInvestmentPrice(Investment investment) {
        BigDecimal currentPrice = stockPriceService.getCurrentStockPrice(investment.getTicker());
        investment.setPrice(currentPrice);
        return investmentRepository.save(investment);
    }

    public void updateAllInvestments() {
        List<Investment> investments = investmentRepository.findAll();
        for (Investment investment : investments) {
            BigDecimal currentPrice = stockPriceService.getCurrentStockPrice(investment.getTicker());
            investment.setCurrentValue(investment.getQuantity().multiply(currentPrice));
            investmentRepository.save(investment);
        }
    }

    public List<Investment> getAllInvestments(int userID) {
        return investmentRepository.findByUserId(userID);
    }

    public void deleteInvestment(Investment investment) {
        investmentRepository.delete(investment);
    }
}
