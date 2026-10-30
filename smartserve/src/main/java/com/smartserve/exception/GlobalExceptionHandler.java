package com.smartserve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidToken(
            InvalidTokenException exception) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(
            UserAlreadyExistsException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
    
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(
            InvalidCredentialsException exception) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }
}





/*@RestControllerAdvice -> It allows this class to handle exceptions thrown from different controllers throughout our application.

So later, when we add exceptions for:
- User not found
- Invalid credentials
- Food not found
- Order not found
- Payment errors
we can handle them centrally instead of writing try-catch in every controller.
 * 
 * 
 * 
Register new user	                           ✅ 200 OK
Duplicate user name								✅ 409 Conflict
Login with correct credentials					✅ 200 OK + JWT
Login with wrong credentials					✅ 401 Unauthorized
Invalid JWT										✅ 401 Unauthorized
 * 
 * 
 */