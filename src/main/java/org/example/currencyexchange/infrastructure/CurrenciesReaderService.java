package org.example.currencyexchange.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.api.exception.ApiNbpException;
import org.example.currencyexchange.api.exception.JsonFromApiProcessingException;
import org.example.currencyexchange.infrastructure.model.CurrencyRates;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CurrenciesReaderService {
    private final ApiService apiService;
    private final ObjectMapper objectMapper;

    public CurrencyRates getCurrenciesAndRates() {
        try {
            CurrencyRates[] currencyRates = objectMapper.readValue(apiService.getApiJson(), CurrencyRates[].class);
            return Arrays.stream(currencyRates)
                    .findFirst()
                    .orElseThrow(ApiNbpException::new);
        } catch (JsonProcessingException e) {
            throw new JsonFromApiProcessingException("Error while processing the data from API" + e.getMessage());
        }
    }
}