package com.ecommerce_data_model.demo.controller;


import com.ecommerce_data_model.demo.account_address.Account;
import com.ecommerce_data_model.demo.account_address.Address;
import com.ecommerce_data_model.demo.repository.AccountRepository;
import com.ecommerce_data_model.demo.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/accounts")
@Slf4j
public class AddressController {
        @Autowired
        private AddressRepository addressRepository;

        @Autowired
        private AccountRepository accountRepository;

    public AddressController(AddressRepository addressRepository, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.addressRepository = addressRepository;
    }

    @PostMapping("/{accountId}/address")
        public ResponseEntity<Address> createAddress(@PathVariable String accountId, @RequestBody Address address) {
            log.info("inside createAddress of Address Controller");
            Optional<Account> optionalAccount = accountRepository.findById(Long.valueOf(accountId));

            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                address.setAccount(account);

                Address savedAddress = addressRepository.save(address);
                return ResponseEntity.ok(savedAddress);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("/{accountId}/address")
        public ResponseEntity<Address> getAddress(@PathVariable Long accountId) {
            log.info("inside getAddress of Address Controller");
            Address address = addressRepository.findById(accountId).orElse(null);

            if (address != null) {
                return ResponseEntity.ok(address);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PutMapping("/{accountId}/address")
        public ResponseEntity<Address> updateAddress(@PathVariable Long accountId, @RequestBody Address updatedAddress) {
            log.info("inside updateAddress of Address Controller");
            Address existingAddress = addressRepository.findById(accountId).orElse(null);

            if (existingAddress != null) {
                existingAddress.setStreet(updatedAddress.getStreet());
                existingAddress.setAptBuilding(updatedAddress.getAptBuilding());

                Address savedAddress = addressRepository.save(existingAddress);
                return ResponseEntity.ok(savedAddress);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{accountId}/address")
        public ResponseEntity<Void> deleteAddress(@PathVariable Long accountId) {
            log.info("inside deleteAddress of Address Controller");
            Address addressToDelete = addressRepository.findById(accountId).orElse(null);

            if (addressToDelete != null) {
                addressRepository.delete(addressToDelete);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

