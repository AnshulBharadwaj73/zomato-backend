package com.example.foodApp.zomato.zomato.strategies.impl;

import com.example.foodApp.zomato.zomato.entities.OrderRequest;
import com.example.foodApp.zomato.zomato.entities.Payment;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import com.example.foodApp.zomato.zomato.repositories.OrderRequestRepository;
import com.example.foodApp.zomato.zomato.repositories.PaymentRepository;
import com.example.foodApp.zomato.zomato.strategies.PaymentStrategy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Primary
public class UPIDiscountStrategiesImpl implements PaymentStrategy {

    private final OrderRequestRepository orderRequestRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {

        OrderRequest orderRequest = payment.getOrderRequest();

        if(orderRequest.getPaymentMethod() == PaymentMethod.UPI){
            Double amount = orderRequest.getPrice()*UPI_DISCOUNT;
            System.out.println(amount+" "+orderRequest.getPrice());
//            orderRequest.setPrice(orderRequest.getPrice());
            payment.setAmount(orderRequest.getPrice()-amount);
        }
        orderRequest.setUpdatedAt(LocalDateTime.now());
        orderRequestRepository.save(orderRequest);

        paymentRepository.save(payment);
    }
}
