package org.example.currencyexchange.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CurrencyAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "currency_code")
    private String currencyCode;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "user_account_uuid")
    private UserAccount userAccount;

    private CurrencyAccount(String currencyCode, double amount, UserAccount userAccount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.userAccount = userAccount;
    }

    public static CurrencyAccount of(String currencyCode, double amount, UserAccount userAccount) {
        return new CurrencyAccount(currencyCode, amount, userAccount);
    }
}
