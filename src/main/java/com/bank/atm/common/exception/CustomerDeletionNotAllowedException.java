package com.bank.atm.common.exception;

public class CustomerDeletionNotAllowedException extends RuntimeException {

    public CustomerDeletionNotAllowedException(String message) {
        super(message);
    }
}
