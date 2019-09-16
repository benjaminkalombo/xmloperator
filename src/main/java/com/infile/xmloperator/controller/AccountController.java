package com.infile.xmloperator.controller;
import com.infile.xmloperator.domain.Account;
import com.infile.xmloperator.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
       return accountService.findAllAccounts();
    }
    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.findAccountById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Account> update(@PathVariable Long id, @RequestBody Account account){

        Account currentAccount = accountService.findAccountById(id);
        if (currentAccount == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentAccount.setDemographic(account.getDemographic());
        accountService.update(account);
        return new ResponseEntity<>(currentAccount, HttpStatus.OK);
    }


}





