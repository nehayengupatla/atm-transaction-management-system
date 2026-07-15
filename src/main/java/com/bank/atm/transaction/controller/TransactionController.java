package com.bank.atm.transaction.controller;

import com.bank.atm.transaction.dto.TransactionResponseDto;
import com.bank.atm.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name="Transaction Management", description = "API for retrieving transaction history")
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    // Get transaction history by account number
    @Operation(summary = "Get Transaction History",
            description = "Retrieves all transactions performed on a bank account")
    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionHistory(
            @PathVariable String accountNumber) {

        List<TransactionResponseDto> response =
                transactionService.getTransactionHistory(accountNumber);

        return ResponseEntity.ok(response);
    }
}