package com.currencytaxcalculation.currencytaxcalculationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyTaxDto {
    private BigDecimal taxRate;
    private Long id;
    private String from;
    private String to;
    private BigDecimal quantity;
    private BigDecimal conversionMultiple;
    private BigDecimal totalCalculatedAmount;
    private String environment;

    private BigDecimal calculatedTax;

    public CurrencyTaxDto(Long id, String from, String to,
                          BigDecimal quantity, BigDecimal conversionMultiple, BigDecimal
                               totalCalculatedAmount, String environment) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.quantity = quantity;
        this.conversionMultiple = conversionMultiple;
        this.totalCalculatedAmount = totalCalculatedAmount;
        this.environment = environment;

    }
    public CurrencyTaxDto(BigDecimal taxRate, Long id, String from, String to,
                          BigDecimal quantity, BigDecimal conversionMultiple, BigDecimal
                               totalCalculatedAmount, String environment) {
        this.taxRate = taxRate;
        this.id = id;
        this.from = from;
        this.to = to;
        this.quantity = quantity;
        this.conversionMultiple = conversionMultiple;
        this.totalCalculatedAmount = totalCalculatedAmount;
        this.environment = environment;
    }
}
