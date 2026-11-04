package com.smartserve.kitchen.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartserve.kitchen.entity.KitchenOrder;
import com.smartserve.kitchen.repository.KitchenRepository;
import com.smartserve.kitchen.service.KitchenService;

@Service
@Transactional
public class KitchenServiceImpl implements KitchenService {

    @Autowired
    private KitchenRepository kitchenRepository;

    @Override
    public KitchenOrder receiveOrder(Long orderId) {

        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }

        if (kitchenRepository.findByOrderId(orderId).isPresent()) {
            throw new IllegalArgumentException(
                    "This order has already been received by the kitchen");
        }

        KitchenOrder kitchenOrder = new KitchenOrder();

        kitchenOrder.setOrderId(orderId);
        kitchenOrder.setKitchenStatus("WAITING");
        kitchenOrder.setReceivedAt(LocalDateTime.now());

        return kitchenRepository.save(kitchenOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public KitchenOrder getKitchenOrderById(Long kitchenOrderId) {

        return kitchenRepository.findById(kitchenOrderId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Kitchen order not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public KitchenOrder getKitchenOrderByOrderId(Long orderId) {

        return kitchenRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Kitchen order not found for this order ID"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<KitchenOrder> getAllKitchenOrders() {

        return kitchenRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<KitchenOrder> getOrdersByStatus(String status) {

        if (status == null ||
                !(status.equals("WAITING")
                || status.equals("PREPARING")
                || status.equals("READY"))) {

            throw new IllegalArgumentException("Invalid kitchen status");
        }

        return kitchenRepository.findByKitchenStatus(status);
    }

    @Override
    public KitchenOrder startPreparing(Long kitchenOrderId) {

        KitchenOrder kitchenOrder = getKitchenOrderById(kitchenOrderId);

        if (!"WAITING".equals(kitchenOrder.getKitchenStatus())) {
            throw new IllegalArgumentException(
                    "Only WAITING orders can start preparation");
        }

        kitchenOrder.setKitchenStatus("PREPARING");
        kitchenOrder.setStartedAt(LocalDateTime.now());

        return kitchenRepository.save(kitchenOrder);
    }

    @Override
    public KitchenOrder markOrderReady(Long kitchenOrderId) {

        KitchenOrder kitchenOrder = getKitchenOrderById(kitchenOrderId);

        if (!"PREPARING".equals(kitchenOrder.getKitchenStatus())) {
            throw new IllegalArgumentException(
                    "Only PREPARING orders can be marked READY");
        }

        kitchenOrder.setKitchenStatus("READY");
        kitchenOrder.setReadyAt(LocalDateTime.now());

        return kitchenRepository.save(kitchenOrder);
    }
}