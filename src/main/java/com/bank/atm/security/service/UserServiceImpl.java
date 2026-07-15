package com.bank.atm.security.service;

import com.bank.atm.common.exception.InvalidCredentialsException;
import com.bank.atm.common.exception.UserAlreadyExistsException;
import com.bank.atm.security.dto.LoginRequestDto;
import com.bank.atm.security.dto.LoginResponseDto;
import com.bank.atm.security.dto.RegisterRequestDto;
import com.bank.atm.security.entity.User;
import com.bank.atm.security.jwt.JwtUtil;
import com.bank.atm.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    // Register User
    @Override
    public void register(RegisterRequestDto requestDto) {

        // Check if username already exists
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        // Create User Entity
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role("USER")
                .build();

        // Save User
        userRepository.save(user);
    }


    // User Login
    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        try{
            // check the username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword()
                    )
            );

        }catch (BadCredentialsException badCredentialsException){
            throw  new InvalidCredentialsException("Invalid username and password.");
        }


        // Generate JWT Token
        String token = jwtUtil.generateToken(requestDto.getUsername());

        // Return JWT Token
        return LoginResponseDto.builder()
                .token(token)
                .build();
    }
}