package com.in28minutes.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class CurrencyExchange {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "currency_from")
	private String from;
	
	@Column(name = "currency_to")
	private String to;

	@Column(name = "conversion_multiple")
	private BigDecimal conversionMultiple;

	private String environment;
}
