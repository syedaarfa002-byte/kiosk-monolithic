package com.smartserve.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.smartserve.order.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Long customerId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private List<OrderItemResponse> items;
}