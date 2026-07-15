package com.bank.atm.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.OneToOne;
import com.bank.atm.account.entity.Account;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    private String address;

    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "customer")
    private Account account;
}