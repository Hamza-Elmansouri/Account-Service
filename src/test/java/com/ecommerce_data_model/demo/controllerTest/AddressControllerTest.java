package com.ecommerce_data_model.demo.controllerTest;


import com.ecommerce_data_model.demo.account_address.Account;
import com.ecommerce_data_model.demo.account_address.Address;
import com.ecommerce_data_model.demo.controller.AddressController;
import com.ecommerce_data_model.demo.repository.AccountRepository;
import com.ecommerce_data_model.demo.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddressControllerTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AccountRepository accountRepository;

    private AddressController addressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addressController = new AddressController(addressRepository, accountRepository);
    }


    @Test
    void testCreateAddressWithExistingAccount() {
        Account existingAccount = new Account();
        existingAccount.setAccountId(123L);
        when(accountRepository.findById(123L)).thenReturn(Optional.of(existingAccount));

        Address address = new Address();
        address.setStreet("123 Main St");

        // Mocking the behavior of addressRepository.save() to make sure we are returning the expected Address instance
        when(addressRepository.save(address)).thenReturn(address);

        ResponseEntity<Address> response = addressController.createAddress("123", address);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(address, response.getBody());
        assertEquals(existingAccount, address.getAccount());

        verify(addressRepository, times(1)).save(address);
    }


    @Test
    void testCreateAddressWithNonExistingAccount() {
        when(accountRepository.findById(123L)).thenReturn(Optional.empty());

        Address address = new Address();
        address.setStreet("123 Main St");

        ResponseEntity<Address> response = addressController.createAddress("123", address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(addressRepository, never()).save(address);
    }

    @Test
    void testGetExistingAddress() {
        Address existingAddress = new Address();
        existingAddress.setStreet("123 Main St");
        when(addressRepository.findById(123L)).thenReturn(Optional.of(existingAddress));

        ResponseEntity<Address> response = addressController.getAddress(123L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingAddress, response.getBody());

        verify(addressRepository, times(1)).findById(123L);
    }

    @Test
    void testGetNonExistingAddress() {
        when(addressRepository.findById(123L)).thenReturn(Optional.empty());

        ResponseEntity<Address> response = addressController.getAddress(123L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(addressRepository, times(1)).findById(123L);
    }

    @Test
    void testUpdateExistingAddress() {
        Address existingAddress = new Address();
        existingAddress.setStreet("123 Main St");
        when(addressRepository.findById(123L)).thenReturn(Optional.of(existingAddress));

        Address updatedAddress = new Address();
        updatedAddress.setStreet("456 Updated St");
        updatedAddress.setAptBuilding("Apt 789");

        // Mocking the behavior of addressRepository.save() to make sure we are returning the expected Address instance
        when(addressRepository.save(existingAddress)).thenReturn(updatedAddress);

        ResponseEntity<Address> response = addressController.updateAddress(123L, updatedAddress);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAddress, response.getBody());

        verify(addressRepository, times(1)).findById(123L);
        verify(addressRepository, times(1)).save(existingAddress);
    }


    @Test
    void testUpdateNonExistingAddress() {
        when(addressRepository.findById(123L)).thenReturn(Optional.empty());

        Address updatedAddress = new Address();
        updatedAddress.setStreet("456 Updated St");
        updatedAddress.setAptBuilding("Apt 789");

        ResponseEntity<Address> response = addressController.updateAddress(123L, updatedAddress);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(addressRepository, times(1)).findById(123L);
        verify(addressRepository, never()).save(any());
    }

    @Test
    void testDeleteExistingAddress() {
        Address existingAddress = new Address();
        existingAddress.setStreet("123 Main St");
        when(addressRepository.findById(123L)).thenReturn(Optional.of(existingAddress));

        ResponseEntity<Void> response = addressController.deleteAddress(123L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(addressRepository, times(1)).findById(123L);
        verify(addressRepository, times(1)).delete(existingAddress);
    }

    @Test
    void testDeleteNonExistingAddress() {
        when(addressRepository.findById(123L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = addressController.deleteAddress(123L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(addressRepository, times(1)).findById(123L);
        verify(addressRepository, never()).delete(any());
    }

}

