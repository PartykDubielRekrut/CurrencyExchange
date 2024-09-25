package org.example.currencyexchange.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {
    private String code;
    private double bid;
    private double ask;
}