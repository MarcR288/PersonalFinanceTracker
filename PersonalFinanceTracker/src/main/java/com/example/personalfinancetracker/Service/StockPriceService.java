package com.example.personalfinancetracker.Service;

import com.example.personalfinancetracker.model.Investment;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class StockPriceService {

    @Value("${alpha-vantage.api-key}")
    private String apikey;

    private static final String BASE_URL = "https://www.alphavantage.co/query";


    public BigDecimal getCurrentStockPrice(String ticker) {
        // Example of calling Alpha Vantage API to fetch stock price
        String url = String.format("%s?function=TIME_SERIES_INTRADAY&symbol=%s&interval=1min&apikey=%s", BASE_URL, ticker, apikey);

        RestTemplate restTemplate = new RestTemplate();
        try {
            // Call the API to fetch the stock data
            String response = restTemplate.getForObject(url, String.class);

            // Parse the response (you may need to use a library like Jackson to map it into an object)
            // For simplicity, assume we extract the current price from the response
            BigDecimal currentPrice = parsePriceFromResponse(response);
            System.out.println("Current price: $" + currentPrice);
            System.out.println("Raw response: " + response);
            return currentPrice;
        } catch (Exception e) {
            // Handle API errors or parsing errors
            throw new RuntimeException("Error fetching stock price", e);
        }
    }

    private BigDecimal parsePriceFromResponse(String response) {
        try {
            // Parse the JSON response using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // Navigate to the Time Series data
            JsonNode timeSeries = rootNode.path("Time Series (1min)");

            // Get the most recent timestamp in the time series (which should be the first element)
            if (timeSeries.isObject() && timeSeries.size() > 0) {
                String latestTimestamp = timeSeries.fieldNames().next();  // Get the first field name, which is the timestamp
                JsonNode latestData = timeSeries.get(latestTimestamp);  // Get the latest data node

                // Extract the closing price from the latest data
                String priceString = latestData.path("4. close").asText();

                System.out.println("Raw response: " + response);
                return new BigDecimal(priceString);  // Return the price as a BigDecimal
            }
        } catch (Exception e) {
            System.err.println("Error parsing stock price from API response");
            e.printStackTrace();
        }

        return BigDecimal.ZERO;  // Return 0 if we can't find the price
    }

    public void updateInvestmentPrice(Investment investment) {
        // Get the current price of the stock
        BigDecimal currentPrice = getCurrentStockPrice(investment.getTicker());

        // Update the current price of the investment
        investment.setCurrentValue(currentPrice.multiply(investment.getQuantity()));

        // Save the updated investment to the database (assuming you have an InvestmentRepository)
        // investmentRepository.save(investment);
    }

}
