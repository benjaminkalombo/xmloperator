package com.infile.xmloperator.service.impl;

import com.infile.xmloperator.domain.Account;
import com.infile.xmloperator.repository.AccountRepository;
import com.infile.xmloperator.service.AccountService;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findAccountById(Long id) {

        return accountRepository.getOne(id);
    }

    @Override
    public List<Account> findAllAccounts() {

       return accountRepository.findAll();
    }

    @Override
    public void update(Account account) {

       Account accountNew = accountRepository.getOne(account.getId());
       accountNew.setDemographic(account.getDemographic());
       accountRepository.save(accountNew);
    }

}
