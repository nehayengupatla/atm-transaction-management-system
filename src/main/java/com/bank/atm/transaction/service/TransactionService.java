package com.bank.atm.transaction.service;

import com.bank.atm.transaction.dto.TransactionResponseDto;
import com.bank.atm.transaction.entity.Transaction;
import com.bank.atm.transaction.enums.TransactionStatus;
import com.bank.atm.transaction.enums.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {


    // Get transaction history by account number
    List<TransactionResponseDto> getTransactionHistory(String accountNumber);


    // Save transaction details
    Transaction saveTransaction(String fromAccount, String toAccount, BigDecimal amount,
                                TransactionType transactionType, TransactionStatus status);

}