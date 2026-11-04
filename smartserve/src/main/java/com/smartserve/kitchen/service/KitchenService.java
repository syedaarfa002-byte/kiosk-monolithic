package com.smartserve.kitchen.service;

import java.util.List;

import com.smartserve.kitchen.entity.KitchenOrder;

public interface KitchenService {

    KitchenOrder receiveOrder(Long orderId);

    KitchenOrder getKitchenOrderById(Long kitchenOrderId);

    KitchenOrder getKitchenOrderByOrderId(Long orderId);

    List<KitchenOrder> getAllKitchenOrders();

    List<KitchenOrder> getOrdersByStatus(String status);

    KitchenOrder startPreparing(Long kitchenOrderId);

    KitchenOrder markOrderReady(Long kitchenOrderId);
}