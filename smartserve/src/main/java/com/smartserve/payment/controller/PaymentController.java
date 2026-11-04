package com.smartserve.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartserve.payment.entity.Payment;
import com.smartserve.payment.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Create an unpaid bill for an order
    @PostMapping("/order/{orderId}")
    public ResponseEntity<Payment> createPayment(
            @PathVariable Long orderId) {

        Payment payment = paymentService.createPayment(orderId);

        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    // Get payment details by payment ID
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(paymentId));
    }

    // Get payment details by order ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByOrderId(orderId));
    }

    // Get all unpaid bills
    @GetMapping("/unpaid")
    public ResponseEntity<List<Payment>> getUnpaidPayments() {

        return ResponseEntity.ok(
                paymentService.getUnpaidPayments());
    }

    // Get all payment records
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments());
    }

    // Mark a bill as paid after collecting cash
    @PutMapping("/{paymentId}/pay")
    public ResponseEntity<Payment> markPaymentAsPaid(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                paymentService.markPaymentAsPaid(paymentId));
    }
}