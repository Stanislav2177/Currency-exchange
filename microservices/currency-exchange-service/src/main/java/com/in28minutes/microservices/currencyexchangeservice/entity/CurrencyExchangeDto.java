package com.in28minutes.microservices.currencyexchangeservice.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class CurrencyExchangeDto {
    private String from;
    private String to;
    private BigDecimal value;
}
