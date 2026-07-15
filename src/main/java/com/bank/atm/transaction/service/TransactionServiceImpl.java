package com.bank.atm.transaction.service.impl;

import com.bank.atm.account.repository.AccountRepository;
import com.bank.atm.common.exception.AccountNotFoundException;
import com.bank.atm.transaction.dto.TransactionResponseDto;
import com.bank.atm.transaction.entity.Transaction;
import com.bank.atm.transaction.enums.TransactionStatus;
import com.bank.atm.transaction.enums.TransactionType;
import com.bank.atm.transaction.repository.TransactionRepository;
import com.bank.atm.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    // Get transaction history by account number
    @Override
    public List<TransactionResponseDto> getTransactionHistory(String accountNumber) {

        // check whether the account exist
        accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found with number: " + accountNumber));

        // get all transactions
        List<Transaction> transactions = transactionRepository.findAll();

        // Create response list
        List<TransactionResponseDto> responseList = new ArrayList<>();

        // Loop through all transactions
        for (Transaction transaction : transactions) {

            // Check whether account is involved in transaction
            if (accountNumber.equals(transaction.getFromAccount())
                    || accountNumber.equals(transaction.getToAccount())) {

                // Convert the entity to DTO
                TransactionResponseDto responseDto = TransactionResponseDto.builder()
                        .transactionId(transaction.getTransactionId())
                        .fromAccount(transaction.getFromAccount())
                        .toAccount(transaction.getToAccount())
                        .amount(transaction.getAmount())
                        .transactionType(transaction.getTransactionType())
                        .status(transaction.getStatus())
                        .createdAt(transaction.getCreatedAt())
                        .build();

                // Add DTO to response list
                responseList.add(responseDto);
            }
        }

        // Return transaction history
        return responseList;
    }


    // Save transaction details
    @Override
    public Transaction saveTransaction(String fromAccount, String toAccount, BigDecimal amount,
                                TransactionType transactionType, TransactionStatus status) {

        // Create transaction entity
        Transaction transaction = Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .transactionType(transactionType)
                .status(status)
                .createdAt(LocalDateTime.now())
                .build();

        // Save transaction
        return transactionRepository.save(transaction);
    }
}