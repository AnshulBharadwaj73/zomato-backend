package com.example.foodApp.zomato.zomato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantWithAddressDto {

    private String restaurantName;
    private String street;
    private String city;
    private String state;
    private String postalCode;
}
