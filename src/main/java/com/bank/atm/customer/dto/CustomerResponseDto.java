package com.bank.atm.customer.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {

    private Long customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String address;

    private LocalDateTime createdAt;
}