package com.bank.atm.transaction.entity;

import com.bank.atm.transaction.enums.TransactionStatus;
import com.bank.atm.transaction.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;


    private String fromAccount;


    private String toAccount;


    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


    private LocalDateTime createdAt;

}