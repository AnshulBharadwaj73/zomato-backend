package com.example.foodApp.zomato.zomato.repositories;

import com.example.foodApp.zomato.zomato.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
//    OrderItem merge(OrderItem orderItem);
}
