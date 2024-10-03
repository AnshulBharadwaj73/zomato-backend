package com.example.foodApp.zomato.zomato.repositories;

import com.example.foodApp.zomato.zomato.entities.DeliveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest,Long> {
    Optional<DeliveryRequest> findByOrderRequestId(Long deliveryId);
}
