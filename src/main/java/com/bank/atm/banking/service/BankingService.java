package com.bank.atm.banking.service;

import com.bank.atm.banking.dto.BalanceResponseDto;
import com.bank.atm.banking.dto.DepositRequestDto;
import com.bank.atm.banking.dto.TransferRequestDto;
import com.bank.atm.banking.dto.WithdrawRequestDto;

public interface BankingService {

    // Get account balance
    BalanceResponseDto getBalance(String accountNumber);

    // Deposit money
    BalanceResponseDto deposit(String accountNumber, DepositRequestDto requestDto);

    // Withdraw money
    BalanceResponseDto withdraw(String accountNumber, WithdrawRequestDto requestDto);

    // Transfer money
    void transfer(String sourceAccountNumber, TransferRequestDto requestDto);
}