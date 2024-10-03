package com.example.foodApp.zomato.zomato.repositories;

import com.example.foodApp.zomato.zomato.entities.OrderRequest;
import com.example.foodApp.zomato.zomato.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRequestRepository extends JpaRepository<OrderRequest,Long> {
    Optional<OrderRequest> getOrderById(Long id);

    Optional<OrderRequest> findByUser(User user);
}
