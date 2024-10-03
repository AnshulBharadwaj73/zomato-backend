package com.example.foodApp.zomato.zomato.strategies.impl;

import com.example.foodApp.zomato.zomato.entities.OrderRequest;
import com.example.foodApp.zomato.zomato.entities.Payment;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import com.example.foodApp.zomato.zomato.repositories.OrderRequestRepository;
import com.example.foodApp.zomato.zomato.strategies.PaymentStrategy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CODDiscountStrategyImpl implements PaymentStrategy {

    private final OrderRequestRepository orderRequestRepository;

    @Override
    public void processPayment(Payment payment) {

        OrderRequest orderRequest = payment.getOrderRequest();

        if(orderRequest.getPaymentMethod() == PaymentMethod.COD){
            Double price = orderRequest.getPrice()+COD_EXTRA_CHARGE;
            orderRequest.setPrice(price);
        }
        orderRequest.setUpdatedAt(LocalDateTime.now());
        orderRequestRepository.save(orderRequest);

    }
}
