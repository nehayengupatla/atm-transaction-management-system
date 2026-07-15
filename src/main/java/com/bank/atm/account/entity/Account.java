package com.bank.atm.account.entity;


import com.bank.atm.account.enums.AccountType;
import com.bank.atm.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;


    @Column(unique = true, nullable = false)
    private String accountNumber;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;


    @Column(nullable = false)
    private BigDecimal balance;


    private LocalDateTime createdAt;


    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}