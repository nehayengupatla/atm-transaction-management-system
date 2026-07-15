package com.bank.atm.common.exception;

public class AccountDeletionNotAllowedException extends RuntimeException {

    public AccountDeletionNotAllowedException(String message) {
        super(message);
    }
}
