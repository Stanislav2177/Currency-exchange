package com.in28minutes.microservices.currencyexchangeservice;

import java.math.BigDecimal;

public class JsonBody {
    private String from;
    private String to;
    private BigDecimal value;

    public JsonBody(String from, String to, BigDecimal value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
