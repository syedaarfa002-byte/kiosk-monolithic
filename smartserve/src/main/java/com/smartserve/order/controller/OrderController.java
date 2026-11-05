package com.smartserve.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartserve.order.dto.OrderRequest;
import com.smartserve.order.dto.OrderResponse;
import com.smartserve.order.entity.OrderStatus;
import com.smartserve.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. Create Order
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest request) {

        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // 2. Get Order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    // 3. Get Orders by Customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                orderService.getOrdersByCustomer(customerId));
    }

    // 4. Update Order Status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, status));
    }

    // 5. Cancel Order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.noContent().build();
    }
}