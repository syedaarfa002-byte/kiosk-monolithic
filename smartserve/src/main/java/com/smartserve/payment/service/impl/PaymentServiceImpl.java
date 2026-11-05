package com.smartserve.payment.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartserve.payment.entity.Payment;
import com.smartserve.payment.repository.PaymentRepository;
import com.smartserve.payment.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Long orderId) {

        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }

        if (paymentRepository.findByOrderId(orderId).isPresent()) {
            throw new IllegalArgumentException(
                    "Payment already exists for this order");
        }

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentMethod("CASH");
        payment.setPaymentStatus("UNPAID");
        payment.setPaymentDate(null);

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentById(Long paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Payment not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentByOrderId(Long orderId) {

        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Payment not found for this order"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getUnpaidPayments() {

        return paymentRepository.findByPaymentStatus("UNPAID");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }

    @Override
    public Payment markPaymentAsPaid(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Payment not found"));

        if ("PAID".equals(payment.getPaymentStatus())) {
            throw new IllegalArgumentException(
                    "This payment has already been paid");
        }

        payment.setPaymentStatus("PAID");
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}