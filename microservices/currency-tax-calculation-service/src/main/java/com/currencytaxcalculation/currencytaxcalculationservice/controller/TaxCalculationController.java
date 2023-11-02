package com.currencytaxcalculation.currencytaxcalculationservice.controller;

import com.currencytaxcalculation.currencytaxcalculationservice.entity.CurrencyTax;
import com.currencytaxcalculation.currencytaxcalculationservice.exception.NoTaxesException;
import com.currencytaxcalculation.currencytaxcalculationservice.service.CurrencyTaxService;
import com.currencytaxcalculation.currencytaxcalculationservice.entity.CurrencyTaxDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/currency-tax-calculation")
public class TaxCalculationController {
    Logger logger = LoggerFactory.getLogger(TaxCalculationController.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CurrencyTaxService service;

    @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyTaxDto> calculateCurrencyTax(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ){
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        uriVariables.put("quantity", String.valueOf(quantity));

        ResponseEntity<CurrencyTaxDto> responseEntity = restTemplate.getForEntity(
                "http://localhost:8100/currency-conversion/from/{from}/to/{to}/quantity/{quantity}",
                CurrencyTaxDto.class, uriVariables
        );

        CurrencyTaxDto currencyTax = responseEntity.getBody();

        if(currencyTax == null){
            ResponseEntity.notFound();
        }

        CurrencyTaxDto calculated = service.calculateTax(currencyTax, from, to, quantity);
        logger.info("DATA IS RECEIVED From the currency-conversion microservice " + calculated);

        return ResponseEntity.ok(calculated);
    }

    @PostMapping("/add-tax-rate/from/{from}/to/{to}/with-rate/{value}")
    public ResponseEntity<CurrencyTax> saveTaxRate(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal value
    ){
        CurrencyTax currencyTax = service.saveTax(value, from, to);

        logger.info("Data is received {}", currencyTax.toString());

        //Optional usage
        BigDecimal one = new BigDecimal(1);
        BigDecimal result = one.divide(value, 10, RoundingMode.HALF_UP);


        CurrencyTax reversedTax = service.saveTax(value, to, from);

        return ResponseEntity.ok(currencyTax);
    }

    @GetMapping("/display-all")
    public ResponseEntity<List<CurrencyTax>> displayAll(){
        List<CurrencyTax> currencyTaxes = service.allTaxesRelationships();
        if(currencyTaxes == null){
            throw new NoTaxesException("No stored taxes");
        }

        return ResponseEntity.ok(currencyTaxes);
    }
}
