package com.smartserve.order.service;

import java.util.List;

import com.smartserve.order.dto.OrderRequest;
import com.smartserve.order.dto.OrderResponse;
import com.smartserve.order.entity.OrderStatus;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrder(Long orderId);

    List<OrderResponse> getOrdersByCustomer(Long customerId);

    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);

    void cancelOrder(Long orderId);
}