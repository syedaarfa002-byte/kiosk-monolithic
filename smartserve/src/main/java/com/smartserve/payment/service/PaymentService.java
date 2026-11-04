package com.smartserve.payment.service;

import java.util.List;

import com.smartserve.payment.entity.Payment;

public interface PaymentService {

    Payment createPayment(Long orderId);

    Payment getPaymentById(Long paymentId);

    Payment getPaymentByOrderId(Long orderId);

    List<Payment> getUnpaidPayments();

    List<Payment> getAllPayments();

    Payment markPaymentAsPaid(Long paymentId);
}