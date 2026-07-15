package com.bank.atm.banking.controller;

import com.bank.atm.banking.dto.BalanceResponseDto;
import com.bank.atm.banking.dto.DepositRequestDto;
import com.bank.atm.banking.dto.TransferRequestDto;
import com.bank.atm.banking.dto.WithdrawRequestDto;
import com.bank.atm.banking.service.BankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name="Banking Operations", description = "APIs for deposit, withdrawal, transfer and balance enquiry")
@RestController
@RequestMapping("/api/v1/banking")
public class BankingController {

    @Autowired
    private BankingService bankingService;


    // Balance Inquiry endpoint
    @Operation(summary = "Check Account Balance",
            description = "Retrieves the current balance of a bank account")
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<BalanceResponseDto> getBalance(@PathVariable String accountNumber) {

        return ResponseEntity.ok(
                bankingService.getBalance(accountNumber));
    }


    // Deposit money endpoint
    @Operation(summary = "Deposit Money",
            description = "Deposits money into the specified bank account")
    @PutMapping("/deposit/{accountNumber}")
    public ResponseEntity<BalanceResponseDto> deposit(@PathVariable String accountNumber,
            @RequestBody @Valid DepositRequestDto requestDto) {

        return ResponseEntity.ok(
                bankingService.deposit(accountNumber, requestDto));
    }


    // Withdraw money endpoint
    @Operation(summary = "Withdraw Money",
            description = "Withdraws money from the specified bank account")
    @PutMapping("/withdraw/{accountNumber}")
    public ResponseEntity<BalanceResponseDto> withdraw(@PathVariable String accountNumber,
            @RequestBody @Valid WithdrawRequestDto requestDto) {

        // Withdraw money
        BalanceResponseDto responseDto =
                bankingService.withdraw(accountNumber, requestDto);

        // Return success response
        return ResponseEntity.ok(responseDto);
    }


    // Transfer money endpoint
    @Operation(summary = "Transfer Money",
            description = "Transfers money from one account to another account")
    @PutMapping("/transfer/{sourceAccountNumber}")
    public ResponseEntity<String> transfer(
            @PathVariable String sourceAccountNumber,
            @RequestBody @Valid TransferRequestDto requestDto) {

        // transfer money
        bankingService.transfer(sourceAccountNumber, requestDto);

        // return success response
        return ResponseEntity.ok("Money transferred successfully");
    }
}