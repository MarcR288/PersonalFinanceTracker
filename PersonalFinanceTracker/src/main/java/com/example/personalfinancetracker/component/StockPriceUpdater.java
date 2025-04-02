package com.example.personalfinancetracker.component;

import com.example.personalfinancetracker.Service.InvestmentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockPriceUpdater {

    private final InvestmentService investmentService;

    public StockPriceUpdater(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @Scheduled(fixedRate = 60000)
    public void updateStockPrice() {
        investmentService.updateAllInvestments();
    }
}
