package org.example.currencyexchange.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.api.model.UserAccountRequest;
import org.example.currencyexchange.api.model.UserAccountResponse;
import org.example.currencyexchange.domain.model.UserAccount;
import org.example.currencyexchange.infrastructure.repository.UserAccountRepository;
import org.example.currencyexchange.util.UserAccountMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    public String createUser(UserAccountRequest userAccountRequest) {
        UserAccount userAccount = userAccountMapper.mapFromRequest(userAccountRequest);
        userAccount.initializeUserAccount(userAccountRequest.getInitialAmount());
        return userAccountRepository.save(userAccount).getId();
    }

    public UserAccountResponse showUserAccountInfo(String uuid) {
        return userAccountRepository.findById(uuid)
                .map(userAccountMapper::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("User's account with provided UUID does not exist"));
    }
}
