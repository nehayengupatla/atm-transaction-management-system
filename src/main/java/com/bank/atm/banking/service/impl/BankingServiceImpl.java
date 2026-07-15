package com.bank.atm.banking.service.impl;

import com.bank.atm.account.entity.Account;
import com.bank.atm.account.repository.AccountRepository;
import com.bank.atm.banking.dto.BalanceResponseDto;
import com.bank.atm.banking.dto.DepositRequestDto;
import com.bank.atm.banking.dto.TransferRequestDto;
import com.bank.atm.banking.dto.WithdrawRequestDto;
import com.bank.atm.banking.service.BankingService;
import com.bank.atm.common.exception.AccountNotFoundException;
import com.bank.atm.common.exception.InsufficientBalanceException;
import com.bank.atm.common.exception.InvalidTransferException;
import com.bank.atm.transaction.enums.TransactionStatus;
import com.bank.atm.transaction.enums.TransactionType;
import com.bank.atm.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BankingServiceImpl implements BankingService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    // Get account balance
    @Override
    public BalanceResponseDto getBalance(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        return BalanceResponseDto.builder()
                .accountNumber(account.getAccountNumber())
                .availableBalance(account.getBalance())
                .build();
    }


    // Deposit money into account
    @Override
    public BalanceResponseDto deposit(String accountNumber, DepositRequestDto requestDto) {

        // find account using account number
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        // get current balance
        BigDecimal currentBalance = account.getBalance();

        // get deposit amount
        BigDecimal depositAmount = requestDto.getAmount();

        // calculate updated balance
        BigDecimal updatedBalance = currentBalance.add(depositAmount);

        // update account balance
        account.setBalance(updatedBalance);

        // save updated account
        Account savedAccount = accountRepository.save(account);

        // Save transaction details
        transactionService.saveTransaction(
                null,
                savedAccount.getAccountNumber(),
                depositAmount,
                TransactionType.DEPOSIT,
                TransactionStatus.SUCCESS
        );

        // prepare response
        return BalanceResponseDto.builder()
                .accountNumber(savedAccount.getAccountNumber())
                .availableBalance(savedAccount.getBalance())
                .build();
    }


    // Withdraw money from account
    @Override
    public BalanceResponseDto withdraw(String accountNumber, WithdrawRequestDto requestDto) {

        // find account using account number
        Optional<Account> optionalAccount =
                accountRepository.findByAccountNumber(accountNumber);

        // check whether account exists
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }

        // get the account details
        Account account = optionalAccount.get();

        // get current balance
        BigDecimal currentBalance = account.getBalance();

        // get withdrawal amount
        BigDecimal withdrawalAmount = requestDto.getAmount();

        // check sufficient balance
        if (currentBalance.compareTo(withdrawalAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // calculate updated balance
        BigDecimal updatedBalance = currentBalance.subtract(withdrawalAmount);

        // update account balance
        account.setBalance(updatedBalance);

        // save updated account
        Account savedAccount = accountRepository.save(account);

        // Save transaction details
        transactionService.saveTransaction(
                savedAccount.getAccountNumber(),
                null,
                withdrawalAmount,
                TransactionType.WITHDRAW,
                TransactionStatus.SUCCESS
        );

        // prepare response
        BalanceResponseDto responseDto = BalanceResponseDto.builder()
                .accountNumber(savedAccount.getAccountNumber())
                .availableBalance(savedAccount.getBalance())
                .build();

        return responseDto;
    }


    // Transfer money between accounts
    @Override
    @Transactional
    public void transfer(String sourceAccountNumber, TransferRequestDto requestDto) {

        // find source account
        Optional<Account> sourceAccountOptional =
                accountRepository.findByAccountNumber(sourceAccountNumber);

        // check source account exists
        if (sourceAccountOptional.isEmpty()) {
            throw new AccountNotFoundException("Source account not found");
        }

        // get source account details
        Account sourceAccount = sourceAccountOptional.get();


        // find destination account
        Optional<Account> destinationAccountOptional = accountRepository.findByAccountNumber(
                        requestDto.getDestinationAccountNumber()
                );

        // check destination account exists
        if (destinationAccountOptional.isEmpty()) {
            throw new AccountNotFoundException("Destination account not found");
        }

        // get destination account details
        Account destinationAccount = destinationAccountOptional.get();


        // check whether source and destination accounts are same
        if (sourceAccount.getAccountNumber().equals(
                destinationAccount.getAccountNumber())) {

            throw new InvalidTransferException(
                    "Source and destination accounts cannot be the same");
        }

        // Get transfer amount
        BigDecimal transferAmount = requestDto.getAmount();


        // get source account balance
        BigDecimal sourceCurrentBalance = sourceAccount.getBalance();


        // check sufficient balance
        if (sourceCurrentBalance.compareTo(transferAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }


        // calculate source account updated balance
        BigDecimal updatedSourceBalance =
                sourceCurrentBalance.subtract(transferAmount);


        // calculate destination account updated balance
        BigDecimal updatedDestinationBalance =
                destinationAccount.getBalance().add(transferAmount);


        // update source account balance
        sourceAccount.setBalance(updatedSourceBalance);


        // update destination account balance
        destinationAccount.setBalance(updatedDestinationBalance);


        // save source account
        Account savedSourceAccount = accountRepository.save(sourceAccount);


        // save destination account
        Account savedDestinationAccount = accountRepository.save(destinationAccount);

        // Save transaction details
        transactionService.saveTransaction(
                sourceAccount.getAccountNumber(),
                destinationAccount.getAccountNumber(),
                transferAmount,
                TransactionType.TRANSFER,
                TransactionStatus.SUCCESS
        );
    }
}