package com.example.foodApp.zomato.zomato.strategies;

import com.example.foodApp.zomato.zomato.entities.Payment;

public interface PaymentStrategy {

    Double UPI_DISCOUNT = 0.05;
    Double COD_EXTRA_CHARGE = 20.0;

    void processPayment(Payment payment);
}
