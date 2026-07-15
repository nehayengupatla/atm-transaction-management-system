package com.bank.atm.customer.controller;

import com.bank.atm.customer.dto.CustomerRequestDto;
import com.bank.atm.customer.dto.CustomerResponseDto;
import com.bank.atm.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@Tag(name="Customer Management", description = "APIs for managing customers")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    // Create Customer Handler
    @Operation(summary = "Create Customer",
            description = "Creates a new bank customer")
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(
            @Valid @RequestBody CustomerRequestDto customerRequestDto) {

        return new ResponseEntity<>(
                customerService.createCustomer(customerRequestDto),
                HttpStatus.CREATED
        );
    }


    // Get all Customers Handler
    @Operation(summary = "Get All Customers",
            description = "Retrieves the list of all customers")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers()
        );
    }


    // Get Customer by Id Handler
    @Operation(summary = "Get Customer By ID",
            description = "Retrieves customer details using customer ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.getCustomerById(id)
        );
    }


    // update Customer Handler
    @Operation(summary = "Update Customer",
            description = "Updates existing customer details")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDto customerRequestDto) {

        return ResponseEntity.ok(
                customerService.updateCustomer(id, customerRequestDto)
        );
    }


    // Delete Customer Handler
    @Operation(summary = "Delete Customer",
            description = "Deletes customer if no active accounts exist")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                "Customer deleted successfully"
        );
    }
}