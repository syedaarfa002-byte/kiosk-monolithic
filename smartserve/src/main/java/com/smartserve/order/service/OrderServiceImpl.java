package com.smartserve.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartserve.order.dto.OrderItemResponse;
import com.smartserve.order.dto.OrderRequest;
import com.smartserve.order.dto.OrderResponse;
import com.smartserve.order.entity.Order;
import com.smartserve.order.entity.OrderItem;
import com.smartserve.order.entity.OrderStatus;
import com.smartserve.order.repository.OrderItemRepository;
import com.smartserve.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 1. Create Order
    @Override
    public OrderResponse createOrder(OrderRequest request) {

        Order order = new Order();

        order.setCustomerId(request.getCustomerId());
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.ZERO);

        order = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemResponse itemRequest : request.getItems()) {

            OrderItem item = new OrderItem();

            item.setFoodId(itemRequest.getFoodId());
            item.setFoodName(itemRequest.getFoodName());
            item.setPrice(itemRequest.getPrice());
            item.setQuantity(itemRequest.getQuantity());
            item.setOrder(order);

            orderItemRepository.save(item);

            BigDecimal itemTotal =
                    itemRequest.getPrice()
                            .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalAmount = totalAmount.add(itemTotal);
        }

        order.setTotalAmount(totalAmount);

        order = orderRepository.save(order);

        return convertToResponse(order);
    }

    // 2. Get Order by ID
    @Override
    public OrderResponse getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return convertToResponse(order);
    }

    // 3. Get Orders by Customer
    @Override
    public List<OrderResponse> getOrdersByCustomer(Long customerId) {

        List<Order> orders =
                orderRepository.findByCustomerId(customerId);

        return orders.stream()
                .map(this::convertToResponse)
                .toList();
    }

    // 4. Update Order Status
    @Override
    public OrderResponse updateOrderStatus(
            Long orderId,
            OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        order = orderRepository.save(order);

        return convertToResponse(order);
    }

    // 5. Cancel Order
    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    // Convert Entity to Response DTO
    private OrderResponse convertToResponse(Order order) {

        List<OrderItem> items =
                orderItemRepository.findByOrderId(order.getId());

        List<OrderItemResponse> itemResponses = items.stream()
                .map(item -> new OrderItemResponse(
                        item.getFoodId(),
                        item.getFoodName(),
                        item.getPrice(),
                        item.getQuantity()))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderDate(),
                itemResponses);
    }
}