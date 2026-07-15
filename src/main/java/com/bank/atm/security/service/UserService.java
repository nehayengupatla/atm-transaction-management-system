package com.bank.atm.security.service;

import com.bank.atm.security.dto.LoginRequestDto;
import com.bank.atm.security.dto.LoginResponseDto;
import com.bank.atm.security.dto.RegisterRequestDto;

public interface UserService {

    void register(RegisterRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto);
}