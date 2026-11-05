package com.smartserve.cart.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private Long cartId;
    private Long customerId;
    private List<CartItemRequest> items;
    private BigDecimal totalAmount;
}