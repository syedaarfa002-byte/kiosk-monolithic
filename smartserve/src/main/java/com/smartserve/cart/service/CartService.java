package com.smartserve.cart.service;

import com.smartserve.cart.dto.CartItemRequest;
import com.smartserve.cart.dto.CartResponse;

public interface CartService {

    CartResponse addToCart(Long customerId, CartItemRequest request);

    CartResponse getCart(Long customerId);

    CartResponse updateCartItem(Long customerId, Long cartItemId, Integer quantity);

    void removeCartItem(Long customerId, Long cartItemId);

    void clearCart(Long customerId);
}