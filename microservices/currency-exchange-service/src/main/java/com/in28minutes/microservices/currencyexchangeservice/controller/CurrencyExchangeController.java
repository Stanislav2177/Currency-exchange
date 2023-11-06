package com.in28minutes.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;
import java.util.List;

import com.in28minutes.microservices.currencyexchangeservice.entity.CurrencyExchange;
import com.in28minutes.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import com.in28minutes.microservices.currencyexchangeservice.entity.CurrencyExchangeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<CurrencyExchange> retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to) {

		logger.info("retrieveExchangeValue called with {} to {}", from, to);


		CurrencyExchange currencyExchange = new CurrencyExchange();
		List<CurrencyExchange> all = repository.findAll();
		for (CurrencyExchange exchange : all) {
			if(exchange.getFrom().equals(from) &&
			exchange.getTo().equals(to)){
				currencyExchange = exchange;
			}
		}



		if(currencyExchange ==null) {
			throw new RuntimeException
				("Unable to Find data for " + from + " to " + to);
		}

		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);

		return ResponseEntity.ok(currencyExchange);
	}

	@GetMapping("/currency-exchange/list-all-exchanges")
	public List<CurrencyExchange> listAllCurrencyExchanges(){
		logger.info("All currency exchanges are received successfully");
		return repository.findAll();
	}

	@PostMapping("/currency-exchange/addExchange")
	public ResponseEntity<CurrencyExchange> saveExchangeValue(
			@RequestBody CurrencyExchangeDto body){
		String from = body.getFrom();
		String to = body.getTo();
		BigDecimal value = body.getValue();

		CurrencyExchange currencyExchange = new CurrencyExchange();

		logger.info("Retrieve data" + from);
		currencyExchange.setFrom(from);
		currencyExchange.setConversionMultiple(value);
		currencyExchange.setTo(to);
		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));

		//The main idea by the logic below is to be save also the
		//relationship between values but in reversed version
		//for example:
		//USD -> BGN : multiple = 2
		//in reversed version would be
		//BGN -> USD : multiple = 1/2
		CurrencyExchange currencyExchangeReversed = new CurrencyExchange();

		BigDecimal one = new BigDecimal(1);
		currencyExchangeReversed.setFrom(to);
		currencyExchangeReversed
				.setConversionMultiple(one.divide
						(value, 10, BigDecimal.ROUND_HALF_UP));
		currencyExchangeReversed.setTo(from);
		currencyExchangeReversed.setEnvironment(environment.getProperty("local.server.port"));


		repository.save(currencyExchange);
		repository.save(currencyExchangeReversed);

		return ResponseEntity.ok(currencyExchange);

	}


		//	@PostMapping("/currency-exchange/from/{from}/to/{to}/with-multiple/{value}")
//	public CurrencyExchange saveExchangeValue(
//			@RequestParam String from,
//			@RequestParam String to,
//			@RequestParam BigDecimal value
//	){
//		CurrencyExchange currencyExchange = new CurrencyExchange();
//
//		logger.info("Retrieve data" + from);
//		currencyExchange.setFrom(from);
//		currencyExchange.setConversionMultiple(value);
//		currencyExchange.setTo(to);
//		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
//
//		//The main idea by the logic below is to be save also the
//		//relationship between values but in reversed version
//		//for example:
//		//USD -> BGN : multiple = 2
//		//in reversed version would be
//		//BGN -> USD : multiple = 1/2
//		CurrencyExchange currencyExchangeReversed = new CurrencyExchange();
//
//		BigDecimal one = new BigDecimal(1);
//		currencyExchangeReversed.setFrom(to);
//		currencyExchangeReversed
//				.setConversionMultiple(one.divide
//						(value, 10, BigDecimal.ROUND_HALF_UP));
//		currencyExchangeReversed.setTo(from);
//		currencyExchangeReversed.setEnvironment(environment.getProperty("local.server.port"));
//
//
//		repository.save(currencyExchange);
//		repository.save(currencyExchangeReversed);
//
//		return currencyExchange;
//	}
//
//	@GetMapping("/currency-exchange/list-all-exchanges")
//	public List<CurrencyExchange> listAllCurrencyExchanges(){
//		logger.info("All currency exchanges are received successfully");
//		return repository.findAll();
//	}
}
