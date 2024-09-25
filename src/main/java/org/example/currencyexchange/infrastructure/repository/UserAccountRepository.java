package org.example.currencyexchange.infrastructure.repository;

import org.example.currencyexchange.domain.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
