
package com.smartserve.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    // Add a new food item
    @PostMapping
    public ResponseEntity<FoodDto> save(
            @Valid @RequestBody FoodDto foodDto,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new IllegalArgumentException(
                    result.getFieldError().getDefaultMessage());
        }

        FoodDto savedFood = foodService.save(foodDto);

        return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
    }

    // Get all food items
    @GetMapping
    public ResponseEntity<List<FoodDto>> getAll() {

        return ResponseEntity.ok(foodService.getAll());
    }

    // Get food by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodDto> getById(
            @PathVariable Long id) throws Exception {

        return ResponseEntity.ok(foodService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodDto> update(
            @PathVariable Long id,
            @Valid @RequestBody FoodDto foodDto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            throw new IllegalArgumentException(
                    result.getFieldError().getDefaultMessage());
        }

        return ResponseEntity.ok(foodService.update(foodDto, id));
    }

    // Delete food by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) throws Exception {

        foodService.delete(id);

        return ResponseEntity.ok("Food deleted successfully");
    }
}
