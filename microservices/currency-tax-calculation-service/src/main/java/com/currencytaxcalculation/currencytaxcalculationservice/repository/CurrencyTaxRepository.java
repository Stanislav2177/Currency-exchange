package com.currencytaxcalculation.currencytaxcalculationservice.repository;

import com.currencytaxcalculation.currencytaxcalculationservice.entity.CurrencyTax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyTaxRepository extends JpaRepository<CurrencyTax, Long> {
}
