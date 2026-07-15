package com.bank.atm.transaction.dto;

import com.bank.atm.transaction.enums.TransactionStatus;
import com.bank.atm.transaction.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDto {

    private Long transactionId;

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;

    private TransactionType transactionType;

    private TransactionStatus status;

    private LocalDateTime createdAt;

}