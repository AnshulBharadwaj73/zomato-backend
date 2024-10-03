package com.example.foodApp.zomato.zomato.repositories;

import com.example.foodApp.zomato.zomato.entities.DeliveryBoy;
import com.example.foodApp.zomato.zomato.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy,Long> {

    @Query("SELECT d FROM DeliveryBoy d WHERE d.user = :user")
    Optional<DeliveryBoy> findByUser(@Param("user") User user);
}
