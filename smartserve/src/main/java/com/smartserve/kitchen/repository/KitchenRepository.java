package com.smartserve.kitchen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartserve.kitchen.entity.KitchenOrder;

public interface KitchenRepository extends JpaRepository<KitchenOrder, Long> {

    Optional<KitchenOrder> findByOrderId(Long orderId);

    List<KitchenOrder> findByKitchenStatus(String kitchenStatus);
}