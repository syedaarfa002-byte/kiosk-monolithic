
package com.smartserve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smartserve.menu.FoodNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

   

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
