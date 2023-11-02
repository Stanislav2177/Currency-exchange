package com.in28minutes.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import com.in28minutes.microservices.currencyconversionservice.entity.CurrencyConversion;
import com.in28minutes.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public ResponseEntity<CurrencyConversion> calculateCurrencyConversion(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from",from);
		uriVariables.put("to",to);
//		uriVariables.put("quantity", String.valueOf(quantity));
		
		ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity
		("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, uriVariables);
		
		CurrencyConversion currencyConversion = responseEntity.getBody();

		CurrencyConversion conversion = new CurrencyConversion(currencyConversion.getId(),
				from, to, quantity,
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()),
				currencyConversion.getEnvironment() + " " + "rest template");
		return ResponseEntity.ok(conversion);
	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
				
		CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to, quantity, 
				currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment() + " " + "feign");
	}
}
