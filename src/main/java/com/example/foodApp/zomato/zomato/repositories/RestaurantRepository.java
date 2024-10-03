package com.example.foodApp.zomato.zomato.repositories;

import com.example.foodApp.zomato.zomato.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Optional<Restaurant> findByName(String restaurantName);

    @Query("SELECT r FROM Restaurant r WHERE r.address.city = :city")
    List<Restaurant> findByCity(String city);

    @Query("SELECT r FROM Restaurant r WHERE r.rating >= :rating")
    List<Restaurant> findByRating(Double rating);
}
