package com.smartserve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.smartserve.menu.FoodNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FoodNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFoodNotFound(FoodNotFoundException e) {
        return e.getMessage();
    }
}