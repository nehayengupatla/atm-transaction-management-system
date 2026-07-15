package com.bank.atm.customer.service;

import com.bank.atm.account.repository.AccountRepository;
import com.bank.atm.common.exception.CustomerAlreadyExistsException;
import com.bank.atm.common.exception.CustomerDeletionNotAllowedException;
import com.bank.atm.common.exception.CustomerNotFoundException;
import com.bank.atm.customer.dto.CustomerRequestDto;
import com.bank.atm.customer.dto.CustomerResponseDto;
import com.bank.atm.customer.entity.Customer;
import com.bank.atm.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;


    // Create Customer
    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {

        if (customerRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomerAlreadyExistsException(
                    "Customer already exists with email: " + requestDto.getEmail());
        }

        if (customerRepository.existsByMobileNumber(requestDto.getMobileNumber())) {
            throw new CustomerAlreadyExistsException(
                    "Customer already exists with mobile number: " + requestDto.getMobileNumber());
        }

        Customer customer = Customer.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .mobileNumber(requestDto.getMobileNumber())
                .address(requestDto.getAddress())
                .createdAt(LocalDateTime.now())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }


    // Get All Customer
    @Override
    public List<CustomerResponseDto> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // Get Customer by Id
    @Override
    public CustomerResponseDto getCustomerById(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with id: " + customerId)
                );

        return mapToResponse(customer);
    }


    // Update Customer
    @Override
    public CustomerResponseDto updateCustomer(Long customerId,
                                              CustomerRequestDto requestDto) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with id: " + customerId)
                );


        customer.setFirstName(requestDto.getFirstName());
        customer.setLastName(requestDto.getLastName());
        customer.setEmail(requestDto.getEmail());
        customer.setMobileNumber(requestDto.getMobileNumber());
        customer.setAddress(requestDto.getAddress());


        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }


    // Delete Customer
    @Override
    public void deleteCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with id: " + customerId)
                );

        if (accountRepository.existsByCustomer(customer)) {
            throw new CustomerDeletionNotAllowedException(
                    "Customer has active accounts. Delete all accounts before deleting the customer.");
        }

        customerRepository.delete(customer);
    }


    private CustomerResponseDto mapToResponse(Customer customer) {

        return CustomerResponseDto.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .address(customer.getAddress())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}