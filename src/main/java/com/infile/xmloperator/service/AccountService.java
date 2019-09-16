package com.infile.xmloperator.service;

import com.infile.xmloperator.domain.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {

    Account findAccountById(Long id);
    List<Account> findAllAccounts();
    void update(Account account);
}
