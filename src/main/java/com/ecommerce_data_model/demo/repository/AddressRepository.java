package com.ecommerce_data_model.demo.repository;

import com.ecommerce_data_model.demo.account_address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
