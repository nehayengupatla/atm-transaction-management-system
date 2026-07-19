package com.bank.atm.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Logger for recording exception details.
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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


    // MethodArgumentNotValidException handling
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        Map<String, String> response = new HashMap<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {

            response.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        return ResponseEntity.badRequest().body(response);
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


    // Generic Exception handling
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {

        logger.error("Unexpected exception occurred.", exception);

        return new ResponseEntity<>(
                buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred. Please try again later."),
                HttpStatus.INTERNAL_SERVER_ERROR);
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