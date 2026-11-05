package com.smartserve.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartserve.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByCustomerId(Long customerId);

}