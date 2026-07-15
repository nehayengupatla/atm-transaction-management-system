package com.bank.atm.account.service;

import com.bank.atm.account.dto.AccountRequestDto;
import com.bank.atm.account.dto.AccountResponseDto;

import java.util.List;

public interface AccountService {

    // Create Account
    AccountResponseDto createAccount(AccountRequestDto requestDto);

    // Get All Accounts
    List<AccountResponseDto> getAllAccounts();

    // Get Account By ID
    AccountResponseDto getAccountById(Long accountId);

    // Update Account
    AccountResponseDto updateAccount(Long accountId, AccountRequestDto requestDto);

    // Delete Account
    void deleteAccount(Long accountId);
}