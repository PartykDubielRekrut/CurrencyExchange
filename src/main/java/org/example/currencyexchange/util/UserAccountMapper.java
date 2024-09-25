package org.example.currencyexchange.util;

import org.example.currencyexchange.api.model.CurrencyAccountDto;
import org.example.currencyexchange.api.model.UserAccountRequest;
import org.example.currencyexchange.api.model.UserAccountResponse;
import org.example.currencyexchange.domain.model.CurrencyAccount;
import org.example.currencyexchange.domain.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserAccountMapper {
    UserAccount mapFromRequest(UserAccountRequest userAccountRequest);

    @Mapping(target = "currencyAccounts", qualifiedByName = "mapCurrencyAccounts")
    UserAccountResponse mapToResponse(UserAccount userAccount);

    @Named(value = "mapCurrencyAccounts")
    static CurrencyAccountDto mapCurrencyAccount(CurrencyAccount currencyAccount) {
        return CurrencyAccountDto.builder()
                .currencyCode(currencyAccount.getCurrencyCode())
                .amount(currencyAccount.getAmount())
                .build();
    }
}
