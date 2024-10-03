package com.example.foodApp.zomato.zomato.repositories;

import com.example.foodApp.zomato.zomato.entities.OrderRequest;
import com.example.foodApp.zomato.zomato.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findById(Long Id);

    Optional<Payment> findByOrderRequest(OrderRequest orderRequest);
}
