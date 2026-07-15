package com.bank.atm.account.dto;

import com.bank.atm.account.enums.AccountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDto {

    private Long accountId;

    private String accountNumber;

    private AccountType accountType;

    private BigDecimal balance;

    private Long customerId;

    private LocalDateTime createdAt;
}