package com.example.foodApp.zomato.zomato.repositories;

import com.example.foodApp.zomato.zomato.entities.MenuItem;
import com.example.foodApp.zomato.zomato.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    Optional<MenuItem> findByName(String restaurantName);

    Optional<MenuItem> findByNameAndRestaurant(String name, Restaurant restaurant);
}
