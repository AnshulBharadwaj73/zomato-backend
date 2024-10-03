package com.example.foodApp.zomato.zomato.services;

import com.example.foodApp.zomato.zomato.entities.OrderRequest;
import com.example.foodApp.zomato.zomato.entities.Payment;

public interface PaymentService {

    Payment createPayment(OrderRequest orderRequest);
    Payment updatePayment(Long orderRequestId);
    void processPayment(OrderRequest orderRequest);
}
