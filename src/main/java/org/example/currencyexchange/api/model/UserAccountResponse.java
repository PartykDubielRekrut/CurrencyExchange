package org.example.currencyexchange.api.model;

import lombok.Data;

import java.util.List;

@Data
public class UserAccountResponse {
    private String name;
    private String surname;
    private List<CurrencyAccountDto> currencyAccounts;
}
