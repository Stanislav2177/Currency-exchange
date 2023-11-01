package com.currencytaxcalculation.currencytaxcalculationservice;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyTaxService {

    public CurrencyTaxService() {
    }

    public CurrencyTax calculateTax(CurrencyTax currencyTax, String from, String to, BigDecimal quantity) {
        BigDecimal taxRate = BigDecimal.valueOf(0.21); // Set your tax rate here
        BigDecimal conversionMultiple = currencyTax.getConversionMultiple();
        BigDecimal total = taxRate.multiply(conversionMultiple).multiply(quantity);

        CurrencyTax calculated = new CurrencyTax(
                currencyTax.getId(),
                from,
                to,
                currencyTax.getQuantity(),
                conversionMultiple,
                quantity.multiply(conversionMultiple),
                currencyTax.getEnvironment() + " rest template"
        );

        calculated.setTaxRate(taxRate);
        calculated.setCalculatedTax(total);

        return calculated;
    }
}
