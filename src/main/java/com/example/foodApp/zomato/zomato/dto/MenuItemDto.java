package com.example.foodApp.zomato.zomato.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {
    private String name;
    private Double price;
    private String description;
    private Boolean isAvailable;
    private Double rating;
    private Long restaurantId;
}
