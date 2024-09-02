package org.robe.fpa.repository;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Account;

public interface AccountRepository {
    List<Account> findAll();
    List<Account> findAllWithInterest();
    Long save(Account account);
    Optional<Account> findById(Long accountId);
    void deleteById(Long accountId);
}
