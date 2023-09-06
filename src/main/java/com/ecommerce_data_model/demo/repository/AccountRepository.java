package com.ecommerce_data_model.demo.repository;

import com.ecommerce_data_model.demo.account_address.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
