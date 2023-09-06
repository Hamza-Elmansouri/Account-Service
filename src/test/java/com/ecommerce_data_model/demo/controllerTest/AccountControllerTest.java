package com.ecommerce_data_model.demo.controllerTest;

import com.ecommerce_data_model.demo.account_address.Account;
import com.ecommerce_data_model.demo.controller.AccountController;
import com.ecommerce_data_model.demo.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountController accountController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountController = new AccountController(accountRepository);
    }

    @Test
    void testCreateAccount() {
        Account account = new Account();
        account.setAccountId(1L);

        ResponseEntity<String> response = accountController.createAccount(account);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Account created successfully", response.getBody());
    }

    @Test
    void testGetAccount() {
        Account account = new Account();
        account.setAccountId(1L);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        ResponseEntity<Account> response = accountController.getAccount(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    void testGetNotFoundAccount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Account> response = accountController.getAccount(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateAccount() {
        Account originalAccount = new Account();
        originalAccount.setAccountId(1L);
        originalAccount.setFirstName("hamza");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(originalAccount));

        Account updatedAccount = new Account();
        updatedAccount.setFirstName("simo");

        ResponseEntity<String> response = accountController.updateAccount(1L, updatedAccount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account updated successfully", response.getBody());

        verify(accountRepository, times(1)).save(originalAccount);
        assertEquals("simo", originalAccount.getFirstName());
    }

    @Test
    void testUpdateAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account updatedAccount = new Account();
        ResponseEntity<String> response = accountController.updateAccount(1L, updatedAccount);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Account not found", response.getBody());
    }

    @Test
    void testDeleteAccount() {
        Account account = new Account();
        account.setAccountId(1L);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        ResponseEntity<String> response = accountController.deleteAccount(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account deleted successfully", response.getBody());

        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = accountController.deleteAccount(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Account not found", response.getBody());
    }

    @Test
    void testGetAllAccounts() {
        Account account1 = new Account();
        account1.setAccountId(1L);
        Account account2 = new Account();
        account2.setAccountId(2L);
        List<Account> expectedAccounts = Arrays.asList(account1, account2);
        when(accountRepository.findAll()).thenReturn(expectedAccounts);

        ResponseEntity<List<Account>> response = accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAccounts, response.getBody());
    }

    @Test
    void testGetAllAccountsEmpty() {
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Account>> response = accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
}
