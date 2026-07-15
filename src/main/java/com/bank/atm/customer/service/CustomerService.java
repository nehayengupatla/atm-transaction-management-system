package com.bank.atm.customer.service;

import com.bank.atm.customer.dto.CustomerRequestDto;
import com.bank.atm.customer.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);

    List<CustomerResponseDto> getAllCustomers();

    CustomerResponseDto getCustomerById(Long customerId);

    CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto customerRequestDto);

    void deleteCustomer(Long customerId);
}