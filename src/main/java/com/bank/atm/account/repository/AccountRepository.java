package com.bank.atm.account.repository;


import com.bank.atm.account.entity.Account;
import com.bank.atm.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long> {


    Optional<Account> findByAccountNumber(String accountNumber);


    boolean existsByCustomer(Customer customer);

}