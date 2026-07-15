package com.bank.atm.banking.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceResponseDto {

    private String accountNumber;
    private BigDecimal availableBalance;
}