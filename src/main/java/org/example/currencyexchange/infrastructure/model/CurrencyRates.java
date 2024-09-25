package org.example.currencyexchange.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRates {
    private String effectiveDate;
    private List<Rate> rates;
}

