
package com.smartserve.cart.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {

    private Long foodId;
    private String foodName;
    private BigDecimal price;
    private Integer quantity;
}
