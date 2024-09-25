package org.example.currencyexchange.domain;

import jakarta.persistence.EntityNotFoundException;
import org.example.currencyexchange.api.exception.ExchangeException;
import org.example.currencyexchange.api.model.ExchangeRequest;
import org.example.currencyexchange.domain.model.CurrencyAccount;
import org.example.currencyexchange.domain.model.UserAccount;
import org.example.currencyexchange.infrastructure.CurrenciesReaderService;
import org.example.currencyexchange.infrastructure.model.CurrencyRates;
import org.example.currencyexchange.infrastructure.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyExchangeService {
    private final CurrenciesReaderService currenciesReaderService;
    private final UserAccountRepository userAccountRepository;
    private LocalDate currencyRatesDate;
    private Map<String, Double> currencyBuyRates;
    private Map<String, Double> currencySellRates;

    public CurrencyExchangeService(CurrenciesReaderService currenciesReaderService, UserAccountRepository userAccountRepository) {
        this.currenciesReaderService = currenciesReaderService;
        this.userAccountRepository = userAccountRepository;
        updateRates();
    }

    public void exchangeCurrencies(ExchangeRequest exchangeRequest, String uuid) {
        UserAccount userAccount = getUserAccount(uuid);
        List<CurrencyAccount> currencyAccounts = userAccount.getCurrencyAccounts();

        CurrencyAccount accountToSell = getAccount(currencyAccounts, exchangeRequest.getCurrencyToBeSold());
        CurrencyAccount accountToBuy = getAccount(currencyAccounts, exchangeRequest.getCurrencyToBeBought());

        validateSufficientFunds(accountToSell, exchangeRequest.getAmount());

        if (isUpdateRequire()) {
            updateRates();
        }

        performExchange(accountToSell, accountToBuy, exchangeRequest);
        userAccountRepository.save(userAccount);
    }

    private void performExchange(CurrencyAccount accountToSell, CurrencyAccount accountToBuy, ExchangeRequest exchangeRequest) {
        String currencyToBeSold = exchangeRequest.getCurrencyToBeSold();
        String currencyToBeBought = exchangeRequest.getCurrencyToBeBought();

        double amountToBeSold = exchangeRequest.getAmount();

        accountToSell.setAmount(accountToSell.getAmount() - amountToBeSold);
        double amountBought;
        if (currencyToBeSold.equals("PLN")) {
            amountBought = amountToBeSold / currencyBuyRates.get(currencyToBeBought);
        } else if (currencyToBeBought.equals("PLN")) {
            amountBought = amountToBeSold * currencySellRates.get(currencyToBeSold);
        } else {
            amountBought = amountToBeSold * currencySellRates.get(currencyToBeSold)
                    / currencyBuyRates.get(currencyToBeBought);
        }
        accountToBuy.setAmount(accountToBuy.getAmount() + amountBought);
    }

    private void updateRates() {
        CurrencyRates currencyRates = currenciesReaderService.getCurrenciesAndRates();
        fillDate(currencyRates);
        fillCurrencyBuyRates(currencyRates);
        fillCurrencySellRates(currencyRates);
    }

    private void validateSufficientFunds(CurrencyAccount accountToSell, double amountToBeSold) {
        if (accountToSell.getAmount() < amountToBeSold) {
            throw new ExchangeException("There is no enough money to make this exchange");
        }
    }

    private UserAccount getUserAccount(String uuid) {
        return userAccountRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Account with provided ID does not exist"));
    }

    private CurrencyAccount getAccount(List<CurrencyAccount> currencyAccounts, String currencyCode) {
        return currencyAccounts
                .stream()
                .filter(t -> t.getCurrencyCode().equals(currencyCode))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("There is no " + currencyCode + " account"));
    }

    private void fillDate(CurrencyRates currencyRates) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.currencyRatesDate = LocalDate.parse(currencyRates.getEffectiveDate(), formatter);
    }

    private void fillCurrencyBuyRates(CurrencyRates currencyRates) {
        this.currencyBuyRates = new HashMap<>();
        currencyRates.getRates()
                .forEach((rate) -> currencyBuyRates.put(rate.getCode(), rate.getAsk()));
    }

    private void fillCurrencySellRates(CurrencyRates currencyRates) {
        this.currencySellRates = new HashMap<>();
        currencyRates.getRates()
                .forEach((rate) -> currencySellRates.put(rate.getCode(), rate.getBid()));
    }

    private boolean isUpdateRequire() {
        return !LocalDate.now().equals(currencyRatesDate);
    }
}