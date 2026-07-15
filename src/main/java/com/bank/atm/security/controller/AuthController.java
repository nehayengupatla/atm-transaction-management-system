package com.bank.atm.security.controller;

import com.bank.atm.security.dto.LoginRequestDto;
import com.bank.atm.security.dto.LoginResponseDto;
import com.bank.atm.security.dto.RegisterRequestDto;
import com.bank.atm.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Management",
        description = "APIs for user registration, login, and JWT authentication")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    // Register User
    @Operation(summary = "Register New User",
            description = "Creates a new application user for authentication")
    @PostMapping("/register")
    public ResponseEntity<String> register( @Valid @RequestBody RegisterRequestDto requestDto) {

        userService.register(requestDto);

        return new ResponseEntity<>(
                "User registered successfully.",
                HttpStatus.CREATED
        );
    }


    // User Login
    @Operation(summary = "User Login",
            description = "Authenticates user credentials and generates JWT token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {

        return ResponseEntity.ok(userService.login(requestDto));
    }
}