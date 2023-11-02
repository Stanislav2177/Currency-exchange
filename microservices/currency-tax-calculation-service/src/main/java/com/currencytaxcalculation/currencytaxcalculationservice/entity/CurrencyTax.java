package com.currencytaxcalculation.currencytaxcalculationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "tax_data")
public class CurrencyTax {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @Column(name = "from_currency")
    private String from;

    @Column(name = "to_currency")
    private String to;

    public CurrencyTax(BigDecimal taxRate, String from, String to) {
        this.taxRate = taxRate;
        this.from = from;
        this.to = to;
    }
}
