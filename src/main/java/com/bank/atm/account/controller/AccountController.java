package com.bank.atm.account.controller;

import com.bank.atm.account.dto.AccountRequestDto;
import com.bank.atm.account.dto.AccountResponseDto;
import com.bank.atm.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name="Account Management", description = "APIs for managing bank accounts")
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    // Create Account Handler
    @Operation(summary = "Open Bank Account",
            description = "Creates a new bank account for a customer")
    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(
            @Valid @RequestBody AccountRequestDto requestDto) {

        return new ResponseEntity<>(
                accountService.createAccount(requestDto),
                HttpStatus.CREATED
        );
    }


    // Get All Accounts Handler
    @Operation(summary = "Retrieve All Bank Accounts",
            description = "Fetches details of all bank accounts")
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {

        return ResponseEntity.ok(
                accountService.getAllAccounts()
        );
    }


    // Get Account By ID handler
    @Operation(summary = "Retrieve Account Details",
            description = "Fetches account details using account ID")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                accountService.getAccountById(id)
        );
    }


    // Update Account Handler
    @Operation(summary = "Update Bank Account",
            description = "Updates account details such as account type and balance")
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequestDto requestDto) {

        return ResponseEntity.ok(
                accountService.updateAccount(id, requestDto)
        );
    }


    // Delete Account Handler
    @Operation(
            summary = "Close Bank Account",
            description = "Deletes an existing bank account")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable Long id) {

        accountService.deleteAccount(id);

        return ResponseEntity.ok("Account deleted successfully");
    }
}