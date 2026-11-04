
package com.smartserve.kitchen.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "kitchen_orders")
public class KitchenOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kitchenOrderId;

    private Long orderId;

    private String kitchenStatus = "WAITING";

    private LocalDateTime receivedAt;

    private LocalDateTime startedAt;

    private LocalDateTime readyAt;
}
    
