package com.bank.atm.account.service;

import com.bank.atm.account.dto.AccountRequestDto;
import com.bank.atm.account.dto.AccountResponseDto;
import com.bank.atm.account.entity.Account;
import com.bank.atm.account.repository.AccountRepository;
import com.bank.atm.common.exception.AccountAlreadyExistsException;
import com.bank.atm.common.exception.AccountDeletionNotAllowedException;
import com.bank.atm.common.exception.AccountNotFoundException;
import com.bank.atm.common.exception.CustomerNotFoundException;
import com.bank.atm.customer.entity.Customer;
import com.bank.atm.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    // Create Account
    @Override
    public AccountResponseDto createAccount(AccountRequestDto requestDto) {

        // check if account number already exists
        accountRepository.findByAccountNumber(requestDto.getAccountNumber())
                .ifPresent(account -> {
                    throw new AccountAlreadyExistsException(
                            "Account already exists with number: " + requestDto.getAccountNumber());
                });

        // find customer by ID
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with id: " + requestDto.getCustomerId()));

        // create Account Entity
        Account account = Account.builder()
                .accountNumber(requestDto.getAccountNumber())
                .accountType(requestDto.getAccountType())
                .balance(requestDto.getBalance())
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .build();

        // save the account
        Account savedAccount = accountRepository.save(account);

        // convert the entity to DTO
        return mapToResponse(savedAccount);
    }

    // get all accounts
    @Override
    public List<AccountResponseDto> getAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // get account by ID
    @Override
    public AccountResponseDto getAccountById(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found with id: " + accountId));

        return mapToResponse(account);
    }

    // update the account
    @Override
    public AccountResponseDto updateAccount(Long accountId,
                                            AccountRequestDto requestDto) {

        // find existing account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found with id: " + accountId));

        // find customer
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with id: " + requestDto.getCustomerId()));

        // update account details
        account.setAccountNumber(requestDto.getAccountNumber());
        account.setAccountType(requestDto.getAccountType());
        account.setBalance(requestDto.getBalance());
        account.setCustomer(customer);

        // save updated account
        Account updatedAccount = accountRepository.save(account);

        // convert Entity to DTO
        return mapToResponse(updatedAccount);
    }


    // delete Account
    @Override
    public void deleteAccount(Long accountId) {

        // Find account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found with id: " + accountId));

        // Business Validation
        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new AccountDeletionNotAllowedException(
                    "Account balance should be zero before deleting the account.");
        }

        // Break the bidirectional relationship
        Customer customer = account.getCustomer();
        if (customer != null) {
            customer.setAccount(null);
        }

        account.setCustomer(null);

        // Delete account
        accountRepository.delete(account);
    }


    // Convert Entity to Response DTO
    private AccountResponseDto mapToResponse(Account account) {

        return AccountResponseDto.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .customerId(account.getCustomer().getCustomerId())
                .createdAt(account.getCreatedAt())
                .build();
    }
}