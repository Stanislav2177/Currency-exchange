package com.currencytaxcalculation.currencytaxcalculationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class TaxCalculationController {
    Logger logger = LoggerFactory.getLogger(TaxCalculationController.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CurrencyTaxService calculateTax;

    @GetMapping("/currency-tax-calculation/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyTax> calculateCurrencyTax(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ){
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        uriVariables.put("quantity", String.valueOf(quantity));

        ResponseEntity<CurrencyTax> responseEntity = restTemplate.getForEntity(
                "http://localhost:8100/currency-conversion/from/{from}/to/{to}/quantity/{quantity}",
                CurrencyTax.class, uriVariables
        );

        CurrencyTax currencyTax = responseEntity.getBody();

        if(currencyTax == null){
            ResponseEntity.notFound();
        }

        CurrencyTax calculated = calculateTax.calculateTax(currencyTax, from, to, quantity);
        logger.info("DATA IS RECEIVED From the currency-conversion microservice " + calculated);

        return ResponseEntity.ok(calculated);
    }


}
