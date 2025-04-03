package com.example.personalfinancetracker.Service;

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

    //alpha vantage URL
    private static final String BASE_URL = "https://www.alphavantage.co/query";


    public BigDecimal getCurrentStockPrice(String ticker) {
        String url = String.format("%s?function=TIME_SERIES_INTRADAY&symbol=%s&interval=1min&apikey=%s", BASE_URL, ticker, apikey);

        RestTemplate restTemplate = new RestTemplate();
        try {
            // Call the API to fetch the stock data
            String response = restTemplate.getForObject(url, String.class);

            // Parse the response
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
        // Return 0 if we can't find the price
        return BigDecimal.ZERO;
    }

}
