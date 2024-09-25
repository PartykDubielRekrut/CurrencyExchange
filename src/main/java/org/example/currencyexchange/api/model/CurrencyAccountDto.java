package org.example.currencyexchange.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyAccountDto {
    private String currencyCode;
    private double amount;
}
