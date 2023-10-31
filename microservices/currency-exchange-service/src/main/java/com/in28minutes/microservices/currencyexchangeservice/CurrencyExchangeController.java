package com.in28minutes.microservices.currencyexchangeservice;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
public class CurrencyExchangeController {
	private Logger logger = LoggerFactory
			.getLogger(CurrencyExchangeController.class);

	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to) {

		logger.info("retrieveExchangeValue called with {} to {}", from, to);

		CurrencyExchange currencyExchange 
					= repository.findByFromAndTo(from, to);
		
		if(currencyExchange ==null) {
			throw new RuntimeException
				("Unable to Find data for " + from + " to " + to);
		}
		
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		
		return currencyExchange;
	}

	@PostMapping("/currency-exchange/{from}/to/{to}/with-multiple/{value}")
	public CurrencyExchange saveExchangeValue(
			@RequestParam String from,
			@RequestParam String to,
			@RequestParam BigDecimal value
	){
		CurrencyExchange currencyExchange = new CurrencyExchange();

		logger.info("Retrieve data" + from);
		currencyExchange.setFrom(from);
		currencyExchange.setConversionMultiple(value);
		currencyExchange.setTo(to);
		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));

		repository.save(currencyExchange);

		return currencyExchange;
	}

	@GetMapping("/currency-exchange/list-all-exchanges")
	public List<CurrencyExchange> listAllCurrencyExchanges(){
		logger.info("All currency exchanges are received successfully");
		return repository.findAll();
	}


}
