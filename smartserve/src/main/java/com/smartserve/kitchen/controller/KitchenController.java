package com.smartserve.kitchen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartserve.kitchen.entity.KitchenOrder;
import com.smartserve.kitchen.service.KitchenService;

@RestController
@RequestMapping("/api/kitchen")
public class KitchenController {

    @Autowired
    private KitchenService kitchenService;

    // Receive an order into the kitchen
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<KitchenOrder> receiveOrder(
            @PathVariable Long orderId) {

        KitchenOrder kitchenOrder = kitchenService.receiveOrder(orderId);

        return new ResponseEntity<>(kitchenOrder, HttpStatus.CREATED);
    }

    // Get all kitchen orders
    @GetMapping("/orders")
    public ResponseEntity<List<KitchenOrder>> getAllKitchenOrders() {

        return ResponseEntity.ok(
                kitchenService.getAllKitchenOrders());
    }

    // Get a kitchen order by its kitchen ID
    @GetMapping("/orders/id/{kitchenOrderId}")
    public ResponseEntity<KitchenOrder> getKitchenOrderById(
            @PathVariable Long kitchenOrderId) {

        return ResponseEntity.ok(
                kitchenService.getKitchenOrderById(kitchenOrderId));
    }

    // Get a kitchen order by the original order ID
    @GetMapping("/orders/order/{orderId}")
    public ResponseEntity<KitchenOrder> getKitchenOrderByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                kitchenService.getKitchenOrderByOrderId(orderId));
    }

    // Get orders by status: WAITING, PREPARING, or READY
    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<KitchenOrder>> getOrdersByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                kitchenService.getOrdersByStatus(status.toUpperCase()));
    }

    // Start food preparation
    @PutMapping("/orders/{kitchenOrderId}/start")
    public ResponseEntity<KitchenOrder> startPreparing(
            @PathVariable Long kitchenOrderId) {

        return ResponseEntity.ok(
                kitchenService.startPreparing(kitchenOrderId));
    }

    // Mark the food as ready
    @PutMapping("/orders/{kitchenOrderId}/ready")
    public ResponseEntity<KitchenOrder> markOrderReady(
            @PathVariable Long kitchenOrderId) {

        return ResponseEntity.ok(
                kitchenService.markOrderReady(kitchenOrderId));
    }
}