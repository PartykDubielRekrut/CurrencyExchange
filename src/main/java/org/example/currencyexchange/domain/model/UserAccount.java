package org.example.currencyexchange.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String surname;
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurrencyAccount> currencyAccounts;

    public void initializeUserAccount(double initialPlnAmount) {
        this.currencyAccounts = new ArrayList<>(List.of(
                CurrencyAccount.of("PLN", initialPlnAmount, this),
                CurrencyAccount.of("USD", 0.0, this)));
    }
}
