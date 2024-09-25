package org.example.currencyexchange.api.model;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.example.currencyexchange.util.validation.CurrencyCode;

@Data
public class ExchangeRequest {
    @CurrencyCode
    private String currencyToBeSold;
    @CurrencyCode
    private String currencyToBeBought;
    @Positive
    private double amount;
}
