package com.example.foodApp.zomato.zomato.repositories;

import com.example.foodApp.zomato.zomato.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
}
