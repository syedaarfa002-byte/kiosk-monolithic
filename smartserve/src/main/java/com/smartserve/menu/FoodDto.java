package com.smartserve.menu;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FoodDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String foodType;
    private String imageUrl;
    private Boolean available;
}