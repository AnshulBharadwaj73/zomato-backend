package com.example.foodApp.zomato.zomato.services;

import com.example.foodApp.zomato.zomato.dto.RestaurantDto;
import com.example.foodApp.zomato.zomato.dto.RestaurantWithAddressDto;
import com.example.foodApp.zomato.zomato.entities.Restaurant;

import java.util.List;

public interface RestaurantService {

    RestaurantDto createNewRestaurant(RestaurantDto restaurantDto);
    List<Restaurant> findRestaurantByCityName(String city);
    List<RestaurantWithAddressDto> findRestaurantByRestaurantRating(Double rating);
    List<RestaurantWithAddressDto> findRestaurantByRestaurantName(String restaurantName);
    void deleteRestaurantWithRestaurantId(Long restaurantId);
}
