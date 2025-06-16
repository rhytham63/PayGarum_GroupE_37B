/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.HashMap;

/**
 *
 * @author utpre
 */
public class CurrencyConverter {
    private static final HashMap<String, Double> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("NPR", 1.0);      // Base currency
        exchangeRates.put("USD", 0.0075);
        exchangeRates.put("EUR", 0.0065);
        exchangeRates.put("GBP", 0.0056);
        exchangeRates.put("AUD", 0.011);
        exchangeRates.put("JPY", 1.09);
        exchangeRates.put("THB", 0.27);
        exchangeRates.put("CNY", 0.052);
        exchangeRates.put("CAD", 0.010);
    }

    public static double convert(double amount, String fromCurrency, String toCurrency) {
        if (exchangeRates.containsKey(fromCurrency) && exchangeRates.containsKey(toCurrency)) {
            double fromRate = exchangeRates.get(fromCurrency);
            double toRate = exchangeRates.get(toCurrency);
            return (amount / fromRate) * toRate;  // Convert to target currency
        } else {
            return 0.0;  // Invalid currency selection
        }
    }

    
}
