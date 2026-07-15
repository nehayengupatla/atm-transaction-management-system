# ATM Transaction Management System

# Project Overview

ATM Transaction Management System is a Spring Boot based backend application that provides banking operations such as customer management, account management, balance enquiry, deposits, withdrawals, fund transfers, and transaction history. The application provides REST APIs with JWT based authentication and uses PostgreSQL as the database.


# Technologies Used
- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Swagger / OpenAPI
- Lombok


# Features
## Authentication Management
- User registration
- User login
- JWT Token based authentication
- Secured REST APIs

## Customer Management
- Create customer
- Retrieve customer details
- Update customer details
- Delete customer

## Account Management
- Create bank account for an existing customers
- Retrieve account details
- Update account details
- Delete account

## Banking Operations
- Balance enquiry
- Deposit money
- Withdraw money
- Transfer money between accounts

## Transaction Management
- Maintain transaction history

## Additional Features
- Global exception handling
- Request validation
- Swagger/OpenAPI documentation


# Application Architecture
The application follows a layered architecture:

Controller Layer → Service Layer → Repository Layer → Database (PostgreSQL)


# Project Structure
The project is organized into different modules based on functionality:-
- customer module - Handles customer related APIs, services, repositories and entities.
- account module - Handles account creation and account management operations.
- banking module - Contains banking operations like deposit, withdrawal, balance enquiry and fund transfer.
- transaction module - Handles transaction history.
- security module - Handles user registration, login, JWT authentication and security related components.
- common module - Contains common components such as configuration and exception handling.
- config module - Contains Swagger/OpenAPI configuration.


# Database Design
Database: PostgreSQL

Main Entities:
- User
- Customer
- Account
- Transaction

Relationships:
- Each customer can have only one account.
- Each account belongs to one customer.
- Transactions maintain the history of account operations.


## Security Implementation
The application uses Spring Security with JWT (JSON Web Token) based authentication.

Authentication flow:
1. User registers into the application
2. User logs in with username and password
3. Server validates credentials and generates the JWT token
4. The client sends the JWT token in the Authorization header with every request to access secured APIs.
5. JWT filter validates the token before allowing access to secured APIs


## API Documentation
Swagger UI
http://localhost:8080/swagger-ui/index.html


## Running the Application
1. Clone the repository using Git Bash or Terminal.
   git clone https://github.com/nehayengupatla/atm-transaction-management-system.git

3. Set the required environment variables:
   - DB_USERNAME
   - DB_PASSWORD
   - JWT_SECRET

4. Run the application.

5. Access Swagger UI.
   http://localhost:8080/swagger-ui/index.html


# Author
Neha Yengupatla
