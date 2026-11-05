package com.smartserve.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartserve.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);

}