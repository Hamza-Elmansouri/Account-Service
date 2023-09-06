package com.ecommerce_data_model.demo.controller;

import com.ecommerce_data_model.demo.account_address.Account;
import com.ecommerce_data_model.demo.repository.AccountRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountController {

    private Map<Long, Account> accounts = new HashMap<>();
    private Long accountId = 0L ;

    @Autowired
    private AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        log.info("inside createAccount Method of Account Controller");
        accountRepository.save(account);
        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        log.info("inside getAccount Method of Account Controller");
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        log.info("inside updateAccount Method of Account Controller");
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setFirstName(updatedAccount.getFirstName());
            account.setLastName(updatedAccount.getLastName());
            account.setEmail(updatedAccount.getEmail());
            account.setAddresses(updatedAccount.getAddresses());
            accountRepository.save(account);
            return new ResponseEntity<>("Account updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        log.info("inside deleteAccount Method of Account Controller");
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(id);
            return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        log.info("inside getAllAccounts Method of Account Controller");
        List<Account> accountList = accountRepository.findAll();
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

}
