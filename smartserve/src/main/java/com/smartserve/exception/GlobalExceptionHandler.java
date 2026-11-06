
package com.smartserve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smartserve.menu.FoodNotFoundException;
import com.smartserve.security.InvalidTokenException;
import com.smartserve.security.UserAlreadyExistsException;
import com.smartserve.security.InvalidCredentialsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle invalid JWT token
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidToken(
            InvalidTokenException exception) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    // Handle duplicate user registration
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(
            UserAlreadyExistsException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    // Handle invalid login credentials
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(
            InvalidCredentialsException exception) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    // Handle food not found
    @ExceptionHandler(FoodNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFoodNotFound(FoodNotFoundException e) {

        return e.getMessage();
    }

    // Handle invalid input and validation errors
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(
            IllegalArgumentException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
