package com.bank.atm.common.exception;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidTransferException extends RuntimeException {

    public InvalidTransferException(String message){
        super(message);
    }
}
