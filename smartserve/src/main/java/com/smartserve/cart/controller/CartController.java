
package com.smartserve.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.smartserve.cart.dto.CartItemRequest;
import com.smartserve.cart.dto.CartResponse;
import com.smartserve.cart.service.CartService;

@RestController
@RequestMapping("/api/cart")
@Component
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{customerId}/items")
    public ResponseEntity<CartResponse> addToCart(
            @PathVariable Long customerId,
            @RequestBody CartItemRequest request) {

        return ResponseEntity.ok(
                cartService.addToCart(customerId, request));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CartResponse> getCart(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                cartService.getCart(customerId));
    }

    @PutMapping("/{customerId}/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable Long customerId,
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                cartService.updateCartItem(
                        customerId,
                        cartItemId,
                        quantity));
    }

    @DeleteMapping("/{customerId}/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            @PathVariable Long customerId,
            @PathVariable Long cartItemId) {

        cartService.removeCartItem(customerId, cartItemId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> clearCart(
            @PathVariable Long customerId) {

        cartService.clearCart(customerId);

        return ResponseEntity.noContent().build();
    }
}
