
package com.smartserve.menu;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data
public class FoodDto {

    private Long id;

    @NotBlank(message = "Food name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Food type is required")
    private String foodType;

    private String imageUrl;

    private Boolean available;
}
