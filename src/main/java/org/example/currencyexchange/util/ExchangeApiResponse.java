package org.example.currencyexchange.util;

public record ExchangeApiResponse(String value) {
    public static ExchangeApiResponse of(String value) {
        return new ExchangeApiResponse(value);
    }
}
