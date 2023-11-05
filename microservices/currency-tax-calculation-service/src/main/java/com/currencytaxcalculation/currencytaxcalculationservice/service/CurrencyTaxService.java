package com.currencytaxcalculation.currencytaxcalculationservice.service;

import com.currencytaxcalculation.currencytaxcalculationservice.entity.CurrencyTax;
import com.currencytaxcalculation.currencytaxcalculationservice.entity.CurrencyTaxDto;
import com.currencytaxcalculation.currencytaxcalculationservice.repository.CurrencyTaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CurrencyTaxService {

    @Autowired
    CurrencyTaxRepository repository;

    public CurrencyTaxService() {
    }

    public CurrencyTaxDto calculateTax(CurrencyTaxDto currencyTax, String from, String to, BigDecimal quantity) {
        BigDecimal taxRate = BigDecimal.valueOf(0.21); // Set your tax rate here
        BigDecimal conversionMultiple = currencyTax.getConversionMultiple();
        BigDecimal total = taxRate.multiply(conversionMultiple).multiply(quantity);

        CurrencyTaxDto calculated = new CurrencyTaxDto(
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

    public CurrencyTax saveTax(BigDecimal taxRate, String from, String to){
        CurrencyTax currencyTax = new CurrencyTax(taxRate, from, to);
        repository.save(currencyTax);


        return currencyTax;
    }

    public CurrencyTax updateTax(CurrencyTax currencyTax){
        repository.delete(currencyTax);
        CurrencyTax save = repository.save(currencyTax);
        return save;
    }

    public List<CurrencyTax> allTaxesRelationships(){
        return repository.findAll();
    }


}
