package com.example.foodApp.zomato.zomato.services.Impl;

import com.example.foodApp.zomato.zomato.entities.OrderRequest;
import com.example.foodApp.zomato.zomato.entities.Payment;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentStatus;
import com.example.foodApp.zomato.zomato.exceptions.OrderNotFoundException;
import com.example.foodApp.zomato.zomato.repositories.OrderRequestRepository;
import com.example.foodApp.zomato.zomato.repositories.PaymentRepository;
import com.example.foodApp.zomato.zomato.services.PaymentService;
import com.example.foodApp.zomato.zomato.strategies.PaymentStrategy;
import com.example.foodApp.zomato.zomato.strategies.PaymentStrategyManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRequestRepository orderRequestRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public Payment createPayment(OrderRequest orderRequest) {
        Payment payment = Payment.builder().orderRequest(orderRequest)
                .isPaid(false)
                .paymentMethod(orderRequest.getPaymentMethod())
                .amount(orderRequest.getPrice())
                .deliveryFee(3.4)
                .tip(orderRequest.getTip()).build();

        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Long orderRequestId) {
        OrderRequest orderRequest = orderRequestRepository.findById(orderRequestId).orElseThrow(()-> new OrderNotFoundException("OrderRequestId not found "+orderRequestId));
        Payment payment =  paymentRepository.findById(orderRequestId).orElseThrow(()-> new RuntimeException("payment with id not found "+orderRequestId));
        orderRequest.setUpdatedAt(LocalDateTime.now());
        orderRequest.setPaymentStatus(PaymentStatus.COMPLETED);

        orderRequestRepository.save(orderRequest);
        payment.setIsPaid(true);

        return paymentRepository.save(payment);
    }

    public void processPayment(OrderRequest orderRequest){
        Payment payment = paymentRepository.findByOrderRequest(orderRequest).orElseThrow(()-> new OrderNotFoundException("Order request not found in Payment Repository"));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }
}
