package com.bank.atm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Account Not Found Exception
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(
            AccountNotFoundException accountNotFoundException) {

        return new ResponseEntity<>(buildErrorResponse(HttpStatus.NOT_FOUND,
                accountNotFoundException.getMessage()),
                HttpStatus.NOT_FOUND);
    }


    // Customer Not Found Exception
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(
            CustomerNotFoundException customerNotFoundException) {

        return new ResponseEntity<>(buildErrorResponse(HttpStatus.NOT_FOUND,
                customerNotFoundException.getMessage()),
                HttpStatus.NOT_FOUND);

    }


    // Insufficient Balance Exception
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(
            InsufficientBalanceException insufficientBalanceException) {

        return new ResponseEntity<>(buildErrorResponse(HttpStatus.BAD_REQUEST,
                insufficientBalanceException.getMessage()),
                HttpStatus.BAD_REQUEST);
    }


    // User already exist exception handling
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException userAlreadyExistsException){

        return new ResponseEntity<>(buildErrorResponse(HttpStatus.CONFLICT,
                userAlreadyExistsException.getMessage()), HttpStatus.CONFLICT);
    }


    // Account already exists exception handling
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAccountAlreadyExistsException(
            AccountAlreadyExistsException accountAlreadyExistsException){

        return new ResponseEntity<>(buildErrorResponse(HttpStatus.CONFLICT,
                accountAlreadyExistsException.getMessage()), HttpStatus.CONFLICT);
    }


    // validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException methodArgumentNotValidException) {


        // Get Validation Error Message
        String message = methodArgumentNotValidException.getBindingResult()
                .getFieldError()
                .getDefaultMessage();


        // Return Error Response
        return new ResponseEntity<>(
                buildErrorResponse(HttpStatus.BAD_REQUEST, message),
                HttpStatus.BAD_REQUEST
        );
    }


    // Invalid username password error handling
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException invalidCredentialsException){

        return new ResponseEntity<>(buildErrorResponse(HttpStatus.UNAUTHORIZED,
                invalidCredentialsException.getMessage()), HttpStatus.UNAUTHORIZED);
    }


    // Customer Deletion Not Allowed Exception handling
    @ExceptionHandler(CustomerDeletionNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleCustomerDeletionNotAllowedException(
            CustomerDeletionNotAllowedException ex) {

        return new ResponseEntity<>(
                buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT);
    }


    // AccountDeletionNotAllowedException handling
    @ExceptionHandler(AccountDeletionNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleAccountDeletionNotAllowedException(
            AccountDeletionNotAllowedException accountDeletionNotAllowedException) {

        return new ResponseEntity<>(
                buildErrorResponse(HttpStatus.CONFLICT, accountDeletionNotAllowedException.getMessage()),
                HttpStatus.CONFLICT);
    }


    // Customer Already Exists Exception handling
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerAlreadyExistsException(
            CustomerAlreadyExistsException customerAlreadyExistsException) {

        return new ResponseEntity<>(
                buildErrorResponse(HttpStatus.CONFLICT, customerAlreadyExistsException.getMessage()),
                HttpStatus.CONFLICT);
    }


    //InvalidTransferException handling
    @ExceptionHandler(InvalidTransferException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransferException(
            InvalidTransferException invalidTransferException){

        return new ResponseEntity<>(buildErrorResponse(HttpStatus.BAD_REQUEST,
                invalidTransferException.getMessage()), HttpStatus.BAD_REQUEST);
    }


    private ErrorResponse buildErrorResponse(HttpStatus status, String message){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .build();

        return errorResponse;
    }
}